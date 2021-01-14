package util;

import java.io.IOException;
import java.util.Base64;

public class StringXOR {

    public String encode(String s, String key) {
            return base64Encode(xorWithKey(s.getBytes(), key.getBytes()));
        }

        public String decode(String s, String key) {
            return new String(xorWithKey(base64Decode(s), key.getBytes()));
        }

        private byte[] xorWithKey(byte[] a, byte[] key) {
            byte[] out = new byte[a.length];
            for (int i = 0; i < a.length; i++) {
                out[i] = (byte) (a[i] ^ key[i%key.length]);
            }
            return out;
        }

        private byte[] base64Decode(String s) {
            try {

                return Base64.getDecoder().decode(s);
            } catch (Exception e) {throw new RuntimeException(e);}


        }

        private String base64Encode(byte[] bytes) {
           return  Base64.getEncoder().encodeToString(bytes);
        }

}
