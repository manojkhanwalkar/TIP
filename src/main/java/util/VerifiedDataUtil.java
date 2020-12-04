package util;

import com.nimbusds.jose.jwk.JWK;
import data.VerifiedData;
import shareablemerkletree.MerkleTree;

public class VerifiedDataUtil {

    public static String toJSON(VerifiedData verifiedData)
    {
        return JSONUtil.toJSON(verifiedData);
    }

    public static VerifiedData create(MerkleTree tree, JWK signPrivateKey )
    {
        VerifiedData verifiedData = new VerifiedData();

        String elements = MerkleTreeUtil.toJSON(tree);
        verifiedData.setElements(elements);

        String signature = SignUtil.sign(elements,signPrivateKey);

        verifiedData.setSignature(signature);

        verifiedData.setPublicKey(signPrivateKey.toPublicJWK().toJSONString());

        return verifiedData;
    }
}
