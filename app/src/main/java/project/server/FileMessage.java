package project.server;

import java.io.DataOutputStream;
import java.io.IOException;

public class FileMessage implements Message {

    private final ChatRoom chatRoom;
    private final Client sender;
    private final String filename;
    private final byte[] bytes;
    private final String senderName;

    public FileMessage(ChatRoom chatRoom, Client sender,
                        String filename, byte[] bytes, String senderName) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.filename = filename;
        this.bytes = bytes;
        this.senderName = senderName;
    }

    @Override
    public void send() throws IOException {
        for (Client client : chatRoom.getClients()) {
            if (client.getId() == sender.getId()) {
                continue;
            }
            DataOutputStream out = new DataOutputStream(client.getSocket().getOutputStream());
            MessageWriter messageWriter = new MessageWriter(out);
            messageWriter.write(filename, bytes, senderName);
        }
    }
}
