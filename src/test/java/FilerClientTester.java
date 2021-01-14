import client.FilerClient;
import com.google.common.io.Files;
import data.FileMetaTuple;
import data.Token;
import org.junit.Test;
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



}
