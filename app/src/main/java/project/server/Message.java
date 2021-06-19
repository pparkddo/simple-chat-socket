package project.server;

/**
 * 클라이언트에게 전송될 메시지를 구현한 클래스
 * Content 에는 일반 MessageContent 또는 FileContent 가 들어갈 수 있다.
 */
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
