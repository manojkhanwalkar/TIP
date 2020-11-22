package wallet;

import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.jwk.JWK;
import data.Attributes;
import data.Token;
import data.VerifyRequest;
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

    public void verify(Attributes data)
    {
        //TODO - call the TIP server and verify the data and get a token back

        VerifyRequest request = new VerifyRequest();
        request.setAttributes(data);
        request.setClientPublicKey(publicKey.toJSONString());

        connector.verify(request);
    }

    public void getVerifiedData()
    {
        // todo - present a token to the tip server and get the verified data back.
    }



}
