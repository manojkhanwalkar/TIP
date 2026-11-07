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
import java.util.regex.Pattern;


public class FilerManager {

    // Tokens are server generated random UUIDs; anything else is rejected before it is used
    // to build a file path (prevents path traversal such as "../../etc/passwd").
    private static final Pattern TOKEN_PATTERN =
            Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");

    private final Path filesDirectory;

    JWK signPrivateKey;
    JWK signPublicKey;

    //TODO - change the keys to static.

    public FilerManager(String filesDirectory)
    {
        this.filesDirectory = Paths.get(filesDirectory).toAbsolutePath().normalize();
        createSignKeys();
    }

    static boolean isValidToken(String token)
    {
        return token != null && TOKEN_PATTERN.matcher(token).matches();
    }

    private Path resolve(String token)
    {
        if (!isValidToken(token)) {
            throw new IllegalArgumentException("invalid token");
        }
        Path path = filesDirectory.resolve(token).normalize();
        if (!path.startsWith(filesDirectory)) {
            throw new IllegalArgumentException("invalid token");
        }
        return path;
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
      Path path = resolve(fileName);

       try {
           Files.writeString(path,fileContents);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   private SymKeyStringTuple getFile(String fileName)
   {
       try {
           Path path = resolve(fileName);
           if (!Files.isRegularFile(path)) {
               return null;
           }
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


        if (token == null || !isValidToken(token.getToken())) {
            return response;
        }

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
