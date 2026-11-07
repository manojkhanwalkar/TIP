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

    FilerWallet wallet = FilerWallet.create("https://localhost:8380/");

    public FilerClient()
    {

    }

    public Token update(String directory, String fileName) throws Exception {

        // creates a wallet
        // sends file to be securely stored to the filer service along with the public key.
        // retrieves the file from the filer
        // decrypts the file contents and recreates the file on the client side.


        var fileStr = getFileContents(directory, fileName);

        Token token = wallet.update(fileStr,fileName);

        System.out.println("Token is " + token.getToken());

        return token;





    }

    public void retrieve(Token token , String outputDir)
    {
        var verData = wallet.retrieve(token);


        writeRetrievedFile(outputDir, verData);


    }

    private static void writeRetrievedFile(String filePath, FileMetaTuple verData) {
        try {
            // The file name comes back from the server: keep only its last path element so a
            // malicious or compromised server cannot write outside the output directory.
            java.nio.file.Path outputDir = java.nio.file.Paths.get(filePath).toAbsolutePath().normalize();
            java.nio.file.Path namePart = verData.getFileName() == null ? null : java.nio.file.Paths.get(verData.getFileName()).getFileName();
            if (namePart == null || namePart.toString().isEmpty() || namePart.toString().equals("..")) {
                throw new IOException("invalid file name returned by server");
            }
            java.nio.file.Path target = outputDir.resolve(namePart).normalize();
            if (!target.startsWith(outputDir)) {
                throw new IOException("invalid file name returned by server");
            }
            File file = target.toFile();

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
