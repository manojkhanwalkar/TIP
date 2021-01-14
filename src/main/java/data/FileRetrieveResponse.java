package data;

public class FileRetrieveResponse {

    String encryptedKey;
    String encryptedFileContents;
    String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getEncryptedKey() {
        return encryptedKey;
    }

    public void setEncryptedKey(String encryptedKey) {
        this.encryptedKey = encryptedKey;
    }

    public String getEncryptedFileContents() {
        return encryptedFileContents;
    }

    public void setEncryptedFileContents(String encryptedFileContents) {
        this.encryptedFileContents = encryptedFileContents;
    }
}
