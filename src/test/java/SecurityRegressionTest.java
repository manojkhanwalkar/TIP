import com.nimbusds.jose.jwk.JWK;
import data.*;
import filer.FilerManager;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.BeforeClass;
import org.junit.Test;
import sample.SampleResource;
import tip.VerificationManager;
import util.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Security;
import java.util.Base64;

import static org.junit.Assert.*;

public class SecurityRegressionTest {

    @BeforeClass
    public static void provider() {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static Attributes attributes() {
        return new AttributesBuilder().givenName("Elm").familyName("Hawking").email("a@b.c").build();
    }

    @Test
    public void aesGcmRoundTripAndTamperDetection() throws Exception {
        JWK client = JWUtil.createKey();
        SymKeyStringTuple t = CryptUtil.encrypt("hello world", client.toECKey().toPublicKey());
        assertEquals("hello world", CryptUtil.decrypt(t.getMessage(), t.getKey(), client.toECKey().toPrivateKey()));

        byte[] c = Base64.getDecoder().decode(t.getMessage());
        c[c.length - 1] ^= 1;
        assertNull(CryptUtil.decrypt(Base64.getEncoder().encodeToString(c), t.getKey(), client.toECKey().toPrivateKey()));

        // same plaintext encrypts differently (random IV)
        SymKeyStringTuple t2 = CryptUtil.encrypt("hello world", client.toECKey().toPublicKey());
        assertNotEquals(t.getMessage(), t2.getMessage());
    }

    @Test
    public void serviceOnlyTrustsPinnedTipKey() throws Exception {
        JWK tipKey = JWUtil.createKey();
        JWK client = JWUtil.createKey();
        VerificationManager vm = new VerificationManager(tipKey);
        SampleResource service = new SampleResource("t", "d", tipKey.toPublicJWK());

        // genuine data issued by the TIP
        Token token = vm.verify(attributes(), client.toPublicJWK());
        TokenResponse resp = vm.get(token);
        String json = CryptUtil.decrypt(resp.getEncryptedVerifiedData(), resp.getEncryptedKey(), client.toECKey().toPrivateKey());
        VerifiedData<String> genuine = (VerifiedData<String>) JSONUtil.fromJSON(json, VerifiedData.class);
        ServiceRequest ok = new ServiceRequest();
        ok.setVerifiedData(genuine);
        assertTrue(service.service(ok).getMessage().endsWith("true"));

        // forged: attacker signs with own key and attaches own public key
        JWK attacker = JWUtil.createKey();
        VerifiedData<String> forged = new VerifiedData<>();
        forged.setElements(genuine.getElements());
        forged.setSignature(SignUtil.sign(genuine.getElements(), attacker));
        forged.setPublicKey(attacker.toPublicJWK().toJSONString());
        ServiceRequest bad = new ServiceRequest();
        bad.setVerifiedData(forged);
        assertTrue(service.service(bad).getMessage().endsWith("false"));

        // malformed signature does not blow up
        forged.setSignature("%%%not base64");
        assertTrue(service.service(bad).getMessage().endsWith("false"));
    }

    @Test
    public void filerRejectsPathTraversalTokens() throws Exception {
        Path dir = Files.createTempDirectory("filer");
        Path secret = dir.getParent().resolve("outside-" + System.nanoTime() + ".json");
        Files.writeString(secret, "{\"key\":\"k\",\"message\":\"m\"}");
        try {
            FilerManager fm = new FilerManager(dir.toString());
            Token evil = new Token();
            evil.setToken("../" + secret.getFileName());
            FileRetrieveResponse r = fm.retrieve(evil);
            assertNull(r.getEncryptedKey());
            assertNull(r.getEncryptedFileContents());

            JWK client = JWUtil.createKey();
            Token good = fm.update(Base64.getEncoder().encodeToString("x".getBytes()), "f.txt", client.toPublicJWK());
            assertNotNull(fm.retrieve(good).getEncryptedFileContents());
        } finally {
            Files.deleteIfExists(secret);
        }
    }

    @Test
    public void polymorphicTypingIsAllowlisted() {
        // a JDK class outside the allowlist must not be instantiated from JSON
        String evil = "{\"elements\":[\"java.net.URL\",\"http://example.invalid/\"],\"signature\":\"s\",\"publicKey\":\"p\"}";
        assertNull(JSONUtil.fromJSON(evil, VerifiedData.class));
        String fine = "{\"elements\":\"plain\",\"signature\":\"s\",\"publicKey\":\"p\"}";
        assertEquals("plain", ((VerifiedData) JSONUtil.fromJSON(fine, VerifiedData.class)).getElements());
    }

    @Test
    public void onlyPublicEcKeysAccepted() {
        JWK key = JWUtil.createKey();
        assertTrue(JWUtil.parsePublicECKey(key.toPublicJWK().toJSONString()).isPresent());
        assertFalse(JWUtil.parsePublicECKey(key.toJSONString()).isPresent());
        assertFalse(JWUtil.parsePublicECKey("not json").isPresent());
        assertFalse(JWUtil.parsePublicECKey(null).isPresent());
    }
}
