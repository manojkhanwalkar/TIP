package wallet;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.jwk.JWK;
import data.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import util.CryptUtil;
import util.JSONUtil;
import util.JWUtil;

import java.security.Security;

public class Wallet {



    public static Wallet create(String tipURL)
    {
        return new Wallet(tipURL);
    }


    JWK privateKey;

    JWK publicKey;

    Token token = null;

    TipConnector connector ;

    private Wallet(String tipURL)
    {
        connector = new TipConnector(tipURL);
        privateKey = JWUtil.createKey();
        publicKey = privateKey.toPublicJWK();

        Security.setProperty("crypto.policy", "unlimited");
        Security.addProvider(new BouncyCastleProvider());

    }

    public synchronized void verify(Attributes data)
    {

        VerifyRequest request = new VerifyRequest();
        request.setAttributes(data);
        request.setClientPublicKey(publicKey.toJSONString());

        token = connector.verify(request).getToken();
    }

    public synchronized VerifiedData getVerifiedData()
    {
        TokenRequest request = new TokenRequest();
        request.setToken(token);

        TokenResponse response = connector.token(request);

        System.out.println(response.getEncryptedKey() + " \n" + response.getEncryptedVerifiedData());

        String resp = null;
        try {
            resp = CryptUtil.decrypt(response.getEncryptedVerifiedData(),response.getEncryptedKey(),privateKey.toECKey().toPrivateKey());
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        VerifiedData verifiedData = (VerifiedData) JSONUtil.fromJSON(resp,VerifiedData.class);

        return verifiedData;

        /*TODO
        decrypt the key using the private key and get the AES key
        use that key to decrypt the message and get the VerifiedData string
        and convert that from JSON to VerifiedData object
        verify the signature using the public key in the verified data object.

        */
    }





}
