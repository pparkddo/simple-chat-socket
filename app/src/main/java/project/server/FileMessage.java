package project.server;

public class FileMessage{
    
    private final ChatRoom chatRoom;
    private final Client sender;
    private final String filename;
    private final int fileSize;
    private final byte[] bytes;

    public FileMessage(ChatRoom chatRoom, Client sender, String filename, int fileSize, byte[] bytes) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.filename = filename;
        this.fileSize = fileSize;
        this.bytes = bytes;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public Client getSender() {
        return sender;
    }

    public String getFilename() {
        return filename;
    }

    public int getFileSize() {
        return fileSize;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
