package tip;

import com.nimbusds.jose.jwk.JWK;
import data.Attributes;
import data.Token;
import util.CryptUtil;
import util.JWUtil;

import javax.crypto.SecretKey;
import java.util.UUID;



public class VerificationManager {

    JWK signPrivateKey;
    JWK signPublicKey;

    public VerificationManager()
    {
        createSignKeys();
    }

    public void verify(Attributes data, JWK clientPublicKey)
    {

        Token token = createToken();
    }



    private void createSignKeys()
    {
        signPrivateKey = JWUtil.createKey();
        signPublicKey = signPrivateKey.toPublicJWK();

    }
    private Token createToken()
    {
        Token token = new Token();
        token.setToken(UUID.randomUUID().toString());

        return token;

    }

}
