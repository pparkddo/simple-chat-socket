package project.server;

/**
 * 일반 채팅 Message 내용을 담고 있는 클래스
 */
public class MessageContent implements Content {

    private final String value;

    public MessageContent(String content) {
        this.value = content;
    }

    public String getValue() {
        return value;
    }
    
    @Override
    public String getType() {
        return "message";
    }
}
