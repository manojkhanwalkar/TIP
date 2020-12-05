package util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;



// using the same keys for signing and key exchange - in practice a separate set of keys will be used.


public class SignUtil {

    private static final String SPEC = "prime256v1";
    private static final String ALGO = "SHA256withECDSA";



    public static  String sign(String textToSign, JWK signKey)  {


        try {
            Signature ecdsaSign = Signature.getInstance(ALGO);
            ecdsaSign.initSign(signKey.toECKey().toPrivateKey());
            ecdsaSign.update(textToSign.getBytes("UTF-8"));
            byte[] signature = ecdsaSign.sign();
            String sig = Base64.getEncoder().encodeToString(signature);


            return sig;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static boolean  verify(String messagetoVerify , String signature , JWK signPublicKey)  {

        try {
            Signature ecdsaVerify = Signature.getInstance(ALGO);

            ecdsaVerify.initVerify(signPublicKey.toECKey().toPublicKey());
            ecdsaVerify.update(messagetoVerify.getBytes("UTF-8"));
            boolean result = ecdsaVerify.verify(Base64.getDecoder().decode(signature));
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return false;
    }



}



