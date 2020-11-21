package wallet;

import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.jwk.JWK;
import data.Token;
import util.JWUtil;

public class Wallet {

    String tipURL;

    public static Wallet create(String tipURL)
    {
        return new Wallet(tipURL);
    }


    JWK privateKey;

    JWK publicKey;

    Token token = null;

    private Wallet(String tipURL)
    {
        this.tipURL = tipURL;
        privateKey = JWUtil.createKey();
        publicKey = privateKey.toPublicJWK();
    }

    public void verify()
    {
        //TODO - call the TIP server and verify the data and get a token back
    }

    public void getVerifiedData()
    {
        // todo - present a token to the tip server and get the verified data back.
    }



}
