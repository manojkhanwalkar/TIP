package util;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static java.util.Base64.getEncoder;

public class CryptUtil {



    private static byte[] extractPublicKeyFromFile(String fileName)
    {
        try {
            File privKeyFile = new File(fileName);
            // read private key DER file
            DataInputStream dis = new DataInputStream(new FileInputStream(privKeyFile));
            byte[] privKeyBytes = new byte[(int)privKeyFile.length()];
            dis.read(privKeyBytes);
            dis.close();

            return privKeyBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static PublicKey getPublicKey(byte[] bytes)
    {
        try {

            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            CertificateFactory fact = CertificateFactory.getInstance("X.509");
            X509Certificate cer = (X509Certificate) fact.generateCertificate(bis);
            PublicKey key = cer.getPublicKey();


       //     System.out.println(cer);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String convertPublicKeyToString(PublicKey key)
    {


        // public key to bytes , bytes to public key
        String publicKeyStr= getEncoder().encodeToString(key.getEncoded());
        return publicKeyStr;

    }


    public static String convertPrivateKeyToString(PrivateKey key)
    {



        String privateKeyStr= getEncoder().encodeToString(key.getEncoded());
        return privateKeyStr;

    }


    public static PublicKey convertStringtoPublicKey(String str, String type)
    {
        try{
            byte[] byteKey = Base64.getDecoder().decode(str.getBytes());
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance(type);

            return kf.generatePublic(X509publicKey);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }


    public static PrivateKey convertStringtoPrivateKey(String str, String type)
    {
        try{
            byte[] byteKey = Base64.getDecoder().decode(str.getBytes());
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            RSAPrivateKey privKey = (RSAPrivateKey) kf.generatePrivate(spec);


           /*
            X509EncodedKeySpec X509privateKey= new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance(type);

            return kf.generatePrivate(X509privateKey); */

           return privKey;
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    static SecureRandom secureRandom = new SecureRandom();

    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_BITS = 128;



    public static SymKeyStringTuple encrypt(String message, PublicKey key)
    {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(256); // The AES key size in number of bits
            SecretKey secKey = generator.generateKey();

            // AES-GCM (authenticated encryption) with a random 96-bit IV, sent as IV || ciphertext.
            // Plain "AES" resolves to AES/ECB/PKCS5Padding, which leaks patterns and has no integrity.
            byte[] iv = new byte[GCM_IV_LENGTH];
            secureRandom.nextBytes(iv);
            Cipher cipher =  Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secKey, new GCMParameterSpec(GCM_TAG_BITS, iv));
            byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));
            byte[] out = new byte[iv.length + cipherText.length];
            System.arraycopy(iv, 0, out, 0, iv.length);
            System.arraycopy(cipherText, 0, out, iv.length, cipherText.length);
            String encryptedMessage = Base64.getEncoder().encodeToString(out);

        
            Cipher cipher1 = Cipher.getInstance("ECIES");
            cipher1.init(Cipher.PUBLIC_KEY, key);
            String encryptedKey = Base64.getEncoder().encodeToString(cipher1.doFinal(secKey.getEncoded()));

            SymKeyStringTuple tuple = new SymKeyStringTuple();
            tuple.key = encryptedKey;
            tuple.message= encryptedMessage;

            return tuple;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String decrypt(String message, String key, PrivateKey privateKey)  {

      try {
          Cipher cipher = Cipher.getInstance("ECIES");
          cipher.init(Cipher.DECRYPT_MODE, privateKey);

          byte[] decryptedKey = cipher.doFinal(Base64.getDecoder().decode(key));

          SecretKey originalKey = new SecretKeySpec(decryptedKey, 0, decryptedKey.length, "AES");
          byte[] in = Base64.getDecoder().decode(message);
          if (in.length < GCM_IV_LENGTH + GCM_TAG_BITS / 8) {
              return null;
          }
          Cipher aesCipher = Cipher.getInstance(AES_TRANSFORMATION);
          aesCipher.init(Cipher.DECRYPT_MODE, originalKey, new GCMParameterSpec(GCM_TAG_BITS, in, 0, GCM_IV_LENGTH));
          byte[] bytePlainText = aesCipher.doFinal(in, GCM_IV_LENGTH, in.length - GCM_IV_LENGTH);
          String plainText = new String(bytePlainText, java.nio.charset.StandardCharsets.UTF_8);

          return plainText;
      } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
      } catch (NoSuchPaddingException e) {
          e.printStackTrace();
      } catch (InvalidKeyException e) {
          e.printStackTrace();
      } catch (IllegalBlockSizeException e) {
          e.printStackTrace();
      } catch (BadPaddingException e) {
          e.printStackTrace();
      } catch (InvalidAlgorithmParameterException | IllegalArgumentException e) {
          e.printStackTrace();
      }

      return null;

  }






}
