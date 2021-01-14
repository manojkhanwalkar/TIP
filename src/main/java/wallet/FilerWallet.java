package wallet;

import com.nimbusds.jose.JOSEException;
import data.*;
import util.CryptUtil;
import util.JSONUtil;

public class FilerWallet extends Wallet{

//TODO - create long lived key , read it from resource , protect the key via a passphrase and get the passphrase from command line while running the program.


    public static FilerWallet create(String tipURL)
    {


        return new FilerWallet(tipURL);
    }


    FilerConnector connector ;

    private FilerWallet(String tipURL)
    {
        super();
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

        /*TODO
        decrypt the key using the private key and get the AES key
        use that key to decrypt the message and get the VerifiedData string
        and convert that from JSON to VerifiedData object
        verify the signature using the public key in the verified data object.

        */
    }



}
