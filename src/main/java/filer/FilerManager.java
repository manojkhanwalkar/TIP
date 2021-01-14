package filer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import data.*;
import shareablemerkletree.AttributeNode;
import shareablemerkletree.MerkleTree;
import util.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class FilerManager {

    String filesDirectory = "/home/manoj/data/filer/";

    JWK signPrivateKey;
    JWK signPublicKey;

    //TODO - change the keys to static.

    public FilerManager()
    {
        createSignKeys();
    }

    public Token update(String fileContents, String fileName , JWK clientPublicKey)
    {

        // create AES key for encryption
        // encrypt the merkle tree
        // encrypt the AES key using the client public key
        // generate token and store token to tuple mapping (tuple contains encrypted AES and merkle tree and TIP signature for the tree)

       try {

            Token token = createToken();

            VerifiedData verifiedData = VerifiedDataUtil.create(fileContents,fileName,signPrivateKey);

            var tuple = CryptUtil.encrypt(VerifiedDataUtil.toJSON(verifiedData),clientPublicKey.toECKey().toPublicKey());

            var tupleString = JSONUtil.toJSON(tuple);

            createFile(token.getToken(),tupleString);

            return token;
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return null ;


    }


   private void createFile(String fileName , String fileContents)
   {
      Path path = Paths.get(filesDirectory+fileName);

       try {
           Files.writeString(path,fileContents);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   private SymKeyStringTuple getFile(String fileName)
   {
       try {
           Path path = Paths.get(filesDirectory+fileName);
           String fileContents = Files.readString(path);
           var tupleString = (SymKeyStringTuple)JSONUtil.fromJSON(fileContents,SymKeyStringTuple.class);

           return tupleString;
       } catch (IOException e) {
           e.printStackTrace();
       }

        return null;
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

    public FileRetrieveResponse retrieve(Token token) {
        FileRetrieveResponse response = new FileRetrieveResponse();


        var tuple = getFile(token.getToken());
        if (tuple!=null)
        {
            response.setEncryptedKey(tuple.getKey());
            var message = tuple.getMessage();
           // var fileMeta = (FileMetaTuple)JSONUtil.fromJSON(message,FileMetaTuple.class);
           // response.setFileName(fileMeta.getFileName());
            response.setEncryptedFileContents(message);

        }

        return response;
    }
}
