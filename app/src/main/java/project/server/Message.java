package project.server;

public class Message {
    
    private final ChatRoom chatRoom;
    private final Client sender;
    private final Content content;

    public Message(ChatRoom chatRoom, Client sender, Content content) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.content = content;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public Client getSender() {
        return sender;
    }

    public Content getContent() {
        return this.content;
    }
}
