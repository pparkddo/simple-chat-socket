package project.server;

public class FileContent implements Content {

    private final String filename;
    private final int fileSize;
    private final byte[] bytes;

    public FileContent(String filename, int fileSize, byte[] bytes) {
        this.filename = filename;
        this.fileSize = fileSize;
        this.bytes = bytes;
    }

    public int getFileSize() {
        return fileSize;
    }

    public String getFilename() {
        return filename;
    }

    public byte[] getBytes() {
        return bytes;
    }
    
    @Override
    public String getType() {
        return "file";
    }
}
