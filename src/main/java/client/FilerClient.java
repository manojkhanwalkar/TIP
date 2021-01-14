package client;

import com.google.common.io.Files;
import data.*;
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

        var fileName = "output.jpg";
        var fileStr = getFileContents("/home/manoj/Downloads", fileName);

        Token token = wallet.update(fileStr,fileName);

        var verData = wallet.retrieve(token);








    }

    static String getFileContents(String filePath, String fileName) throws IOException {
        byte[] bytes = Files.toByteArray(new File(filePath + "/" + fileName));

        String fileStr = Base64.getEncoder().encodeToString(bytes);
        return fileStr;

    }
}
