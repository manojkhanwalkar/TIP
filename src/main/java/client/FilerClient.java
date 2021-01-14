package client;

import com.google.common.io.Files;
import com.nimbusds.jose.JOSEException;
import data.*;
import util.CryptUtil;
import util.JSONUtil;
import util.MerkleTreeUtil;
import wallet.FilerWallet;
import wallet.TipWallet;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class FilerClient {

    public static void main(String[] args) throws Exception {

        // creates a wallet
        // sends file to be securely stored to the filer service along with the public key.
        // retrieves the file from the filer
        // decrypts the file contents and recreates the file on the client side.

        FilerWallet wallet = FilerWallet.create("https://localhost:8380/");

        var fileName = "README.txt";
        var fileStr = getFileContents("/home/manoj/Downloads", fileName);

        Token token = wallet.update(fileStr,fileName);

        System.out.println("Token is " + token.getToken());

        var verData = wallet.retrieve(token);


        writeRetrievedFile("/tmp/", verData);





    }

    private static void writeRetrievedFile(String filePath, FileMetaTuple verData) {
        try {
            File file = new File(filePath+verData.getFileName());

            byte[] bytes = Base64.getDecoder().decode(verData.getFileContents());

            Files.write(bytes,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static String getFileContents(String filePath, String fileName) throws IOException {
        byte[] bytes = Files.toByteArray(new File(filePath + "/" + fileName));

        String fileStr = Base64.getEncoder().encodeToString(bytes);
        return fileStr;

    }
}
