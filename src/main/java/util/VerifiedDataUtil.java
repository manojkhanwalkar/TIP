package util;

import com.nimbusds.jose.jwk.JWK;
import data.FileMetaTuple;
import data.VerifiedData;
import shareablemerkletree.MerkleTree;

public class VerifiedDataUtil {

    public static String toJSON(VerifiedData verifiedData)
    {
        return JSONUtil.toJSON(verifiedData);
    }

    public static VerifiedData create(MerkleTree tree, JWK signPrivateKey )
    {
        String elements = MerkleTreeUtil.toJSON(tree);
        return create(elements,signPrivateKey);


    }

    public static VerifiedData create(String fileContents, String fileName , JWK signPrivateKey )
    {
        var fileMeta = new FileMetaTuple(fileContents,fileName);
        String elements = JSONUtil.toJSON(fileMeta);
        return create(elements,signPrivateKey);


    }

    private static VerifiedData create(String elements, JWK signPrivateKey )
    {
        VerifiedData verifiedData = new VerifiedData();


        verifiedData.setElements(elements);

        String signature = SignUtil.sign(elements,signPrivateKey);

        verifiedData.setSignature(signature);

        verifiedData.setPublicKey(signPrivateKey.toPublicJWK().toJSONString());

        return verifiedData;
    }


}
