package data;

public class FileMetaTuple {
    String fileContents;
    String fileName;

    public FileMetaTuple(String fileContents, String fileName) {
        this.fileContents = fileContents;
        this.fileName = fileName;
    }

    public FileMetaTuple() {
    }

    public String getFileContents() {
        return fileContents;
    }

    public void setFileContents(String fileContents) {
        this.fileContents = fileContents;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
