package data;

public class FileUpdateRequest {

    String base64FileContents;
    String clientPublicKey;
    String fileName;

    public String getBase64FileContents() {
        return base64FileContents;
    }

    public void setBase64FileContents(String base64FileContents) {
        this.base64FileContents = base64FileContents;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getClientPublicKey() {
        return clientPublicKey;
    }

    public void setClientPublicKey(String clientPublicKey) {
        this.clientPublicKey = clientPublicKey;
    }
}
