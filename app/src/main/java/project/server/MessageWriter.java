package project.server;

import java.io.DataOutputStream;
import java.io.IOException;

public class MessageWriter {

    private static final String FILE_TYPE = "file";
    private static final String CHAT_TYPE = "message";
    private final DataOutputStream out;

    public MessageWriter(DataOutputStream out) {
        this.out = out;
    }
    
    public void write(String content) throws IOException {
        out.writeUTF(CHAT_TYPE);
        out.writeUTF(content);
    }

    public void write(String filename, byte[] bytes, String senderName) throws IOException {
        out.writeUTF(FILE_TYPE);
        out.writeUTF(senderName + "님이 파일을 전송하였습니다(" + filename + ")");
        out.writeUTF(filename);
        out.writeInt(bytes.length);
        out.write(bytes);
    }
}
