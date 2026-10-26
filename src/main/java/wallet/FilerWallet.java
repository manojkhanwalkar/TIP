package wallet;

import com.google.common.io.Resources;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import data.*;
import util.CryptUtil;
import util.JSONUtil;
import util.JWUtil;

import java.net.URL;
import java.nio.charset.Charset;

public class FilerWallet extends Wallet{

//TODO - create long lived key , read it from resource , protect the key via a passphrase and get the passphrase from command line while running the program.


    public static FilerWallet create(String tipURL)
    {
        // The wallet's long lived private key is kept outside the repository and the jar.
        // Its location comes from -Dtip.wallet.key=<path> or the TIP_WALLET_KEY_PATH env variable.
        String keyPath = System.getProperty("tip.wallet.key", System.getenv("TIP_WALLET_KEY_PATH"));
        if (keyPath == null || keyPath.isBlank()) {
            throw new IllegalStateException("Set -Dtip.wallet.key or TIP_WALLET_KEY_PATH to the wallet private key (JWK) file");
        }
        var key = JWUtil.readJWKFromFile(keyPath);
        if (key == null || !key.isPrivate()) {
            throw new IllegalStateException("Could not read a private wallet key from " + keyPath);
        }


        return new FilerWallet(tipURL,key);
    }


    FilerConnector connector ;

    private FilerWallet(String tipURL, JWK key)
    {
        super(key);
        connector = new FilerConnector(tipURL);

    }

    public Token update(String fileStr, String fileName)
    {
        FileUpdateRequest request = new FileUpdateRequest();
        request.setClientPublicKey(publicKey.toJSONString());
        request.setFileName(fileName);
        request.setBase64FileContents(fileStr);

        Token token = connector.verify(request).getToken();

        return token;
    }

    public FileMetaTuple retrieve(Token token)
    {
        TokenRequest request = new TokenRequest();
        request.setToken(token);

        FileRetrieveResponse response = connector.retrieve(request);


        String resp = null;
        try {
            resp = CryptUtil.decrypt(response.getEncryptedFileContents(),response.getEncryptedKey(),privateKey.toECKey().toPrivateKey());
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        VerifiedData<String> verifiedData = (VerifiedData) JSONUtil.fromJSON(resp,VerifiedData.class);


        FileMetaTuple fileMetaTuple = (FileMetaTuple) JSONUtil.fromJSON(verifiedData.getElements(),FileMetaTuple.class);


        return fileMetaTuple;

    }



}
