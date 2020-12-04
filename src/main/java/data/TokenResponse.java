package data;

public class TokenResponse {

    String encryptedKey;
    String encryptedVerifiedData;

    public String getEncryptedKey() {
        return encryptedKey;
    }

    public void setEncryptedKey(String encryptedKey) {
        this.encryptedKey = encryptedKey;
    }

    public String getEncryptedVerifiedData() {
        return encryptedVerifiedData;
    }

    public void setEncryptedVerifiedData(String encryptedVerifiedData) {
        this.encryptedVerifiedData = encryptedVerifiedData;
    }
}
