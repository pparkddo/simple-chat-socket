package project.server;

public class Message {
    
    private final int chatRoomId;
    private final int senderId;
    private final String content;

    public Message(int chatRoomId, int senderId, String content) {
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.content = content;
    }

    public int getChatRoomId() {
        return this.chatRoomId;
    }

    public int getSenderId() {
        return this.senderId;
    }

    public String getContent() {
        return this.content;
    }
}
