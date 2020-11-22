package data;

import com.nimbusds.jose.jwk.JWK;

public class VerifyRequest {

    Attributes attributes;

    String clientPublicKey;

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public String getClientPublicKey() {
        return clientPublicKey;
    }

    public void setClientPublicKey(String clientPublicKey) {
        this.clientPublicKey = clientPublicKey;
    }
}
