package project.server;

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
