package tip;

import com.nimbusds.jose.jwk.JWK;
import data.Attributes;
import data.Token;
import shareablemerkletree.AttributeNode;
import shareablemerkletree.MerkleTree;
import util.CryptUtil;
import util.JWUtil;
import util.MerkleTreeUtil;
import util.RandomIdGenerator;

import javax.crypto.SecretKey;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class VerificationManager {

    JWK signPrivateKey;
    JWK signPublicKey;

    public VerificationManager()
    {
        createSignKeys();
    }

    public void verify(Attributes data, JWK clientPublicKey)
    {
        // create Merkle tree from the attributes
        // create AES key for encryption
        // encrypt the merkle tree
        // encrypt the AES key using the client public key
        // generate token and store token to tuple mapping (tuple contains encrypted AES and merkle tree and TIP signature for the tree)

        MerkleTree tree = generateTree(data);
        Token token = createToken();
    }

    private MerkleTree generateTree(Attributes data)
    {

        try {
            Class attribClass = data.getClass();
            Method[] methods = attribClass.getDeclaredMethods();
            for (Method method :
                    methods) {
                if (method.getName().startsWith("get")){
                    System.out.println(method.getName() + " " + method.invoke(data));
                }
            }
        } catch ( IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        List<AttributeNode> attributeNodes = new ArrayList<>();


        AttributeNode attribNode1 = new AttributeNode();
        attribNode1.setName("FNAME");
        attribNode1.setValue("Hello");
        attribNode1.setId(RandomIdGenerator.getId());

        AttributeNode attribNode2 = new AttributeNode();
        attribNode2.setName("LNAME");
        attribNode2.setValue("World");
        attribNode2.setId(RandomIdGenerator.getId());


        AttributeNode attribNode3 = new AttributeNode();
        attribNode3.setName("SSN");
        attribNode3.setValue("100-20-300");
        attribNode3.setId(RandomIdGenerator.getId());

        MerkleTreeUtil util = new MerkleTreeUtil();
        MerkleTree tree = util.build(List.of(attribNode1,attribNode2,attribNode3));
        return tree;

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

}
