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

public abstract class Wallet {



    JWK privateKey;

    JWK publicKey;

    Token token = null;

    static
    {
        Security.setProperty("crypto.policy", "unlimited");
        Security.addProvider(new BouncyCastleProvider());
    }

    protected Wallet()
    {

        privateKey = JWUtil.createKey();
        publicKey = privateKey.toPublicJWK();

      /*  Security.setProperty("crypto.policy", "unlimited");
        Security.addProvider(new BouncyCastleProvider());*/

    }

    protected Wallet(JWK key)
    {
        this.privateKey = key;
        this.publicKey = privateKey.toPublicJWK();
    }



}
