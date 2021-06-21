package project.client;

import java.io.DataInputStream;

public class MessageReceiverFactory {

    private final DataInputStream in;
    private final String downloadPath;
    private static final String FILE_TYPE = "file";
    private static final String CHAT_TYPE = "message";
    
    public MessageReceiverFactory(DataInputStream in, String downloadPath) {
        this.in = in;
        this.downloadPath = downloadPath;
    }

    public MessageReceiver get(String type) {
        if (FILE_TYPE.equals(type)) {
            return new FileMessageReceiver(in, downloadPath);
        }
        if (CHAT_TYPE.equals(type)) {
            return new ChatMessageReceiver(in);
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }
}
