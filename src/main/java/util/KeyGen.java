package util;

import com.nimbusds.jose.jwk.JWK;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;

/**
 * Generates a P-256 EC key pair as JWK files:
 *   java -cp TIP.jar util.KeyGen <private-key.json> [public-key.json]
 * The private key file is created readable by the owner only (where the file system supports it).
 */
public class KeyGen {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("usage: util.KeyGen <private-key.json> [public-key.json]");
            System.exit(1);
        }
        JWK key = JWUtil.createKey();
        writeOwnerOnly(Paths.get(args[0]), key.toJSONString());
        if (args.length > 1) {
            Files.writeString(Paths.get(args[1]), key.toPublicJWK().toJSONString());
        }
    }

    private static void writeOwnerOnly(Path path, String contents) throws IOException {
        try {
            Files.createFile(path, PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-------")));
        } catch (UnsupportedOperationException e) {
            Files.createFile(path);
        }
        Files.writeString(path, contents);
    }
}
