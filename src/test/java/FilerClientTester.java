import client.FilerClient;
import com.google.common.io.Files;
import com.nimbusds.jose.jwk.JWK;
import data.FileMetaTuple;
import data.Token;
import org.junit.Test;
import util.JWUtil;
import wallet.FilerConnector;
import wallet.FilerWallet;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class FilerClientTester {

    @Test
    public void testFileUploadAndRetrieval() throws Exception {

        FilerClient client = new FilerClient();
        var fileName = "README.txt";
        var token = client.update("/home/manoj/Downloads",fileName);

        System.out.println("Token is " + token.getToken());

        client.retrieve(token,"/tmp/");

        assert(new File("/tmp/"+fileName).isFile());


    }

    @Test
    public void testRetrieval() throws Exception
    {
        FilerClient client = new FilerClient();
        Token token = new Token();
        token.setToken("c012a4e7-bdf7-4a1a-9dd8-e69fbeced7ff");
        client.retrieve(token,"/tmp/");



    }

    @Test
    public void passPhraseEncoding()
    {
        JWK jwk = JWUtil.createKey();

        String passPhrase = "The quick brown fox jumped over the hedge";

        var v1 = JWUtil.keyWithPassPhrase(jwk,passPhrase);

        var validKey = JWUtil.keyFromStringWithPassPhrase(v1,passPhrase);

        assert (jwk.equals(validKey));


    }

}
