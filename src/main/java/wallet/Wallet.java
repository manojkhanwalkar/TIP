package wallet;

import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.jwk.JWK;
import data.*;
import util.JWUtil;

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
    }

    public synchronized void verify(Attributes data)
    {

        VerifyRequest request = new VerifyRequest();
        request.setAttributes(data);
        request.setClientPublicKey(publicKey.toJSONString());

        token = connector.verify(request).getToken();
    }

    public synchronized void getVerifiedData()
    {
        TokenRequest request = new TokenRequest();
        request.setToken(token);

        TokenResponse response = connector.token(request);

        System.out.println(response.getEncryptedKey() + " \n" + response.getEncryptedVerifiedData());

        /*TODO
        decrypt the key using the private key and get the AES key
        use that key to decrypt the message and get the VerifiedData string
        and convert that from JSON to VerifiedData object
        verify the signature using the public key in the verified data object.

        */
    }



}
