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

        System.out.println(MerkleTreeUtil.toJSON(tree));

    }

    private MerkleTree generateTree(Attributes data)
    {
        List<AttributeNode> attributeNodes = new ArrayList<>();

        try {
            Class attribClass = data.getClass();
            Method[] methods = attribClass.getDeclaredMethods();
            for (Method method :
                    methods) {
                if (method.getName().startsWith("get")){
                    //System.out.println(method.getName() + " " + method.invoke(data));

                    AttributeNode attribNode = new AttributeNode();
                    attribNode.setName(method.getName());
                    attribNode.setValue((String)method.invoke(data));
                    attribNode.setId(RandomIdGenerator.getId());

                    attributeNodes.add(attribNode);

                }


            }
        } catch ( IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        MerkleTree tree = MerkleTreeUtil.build(attributeNodes);
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
