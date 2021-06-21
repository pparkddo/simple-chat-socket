package project.server;

import java.io.DataOutputStream;
import java.io.IOException;

public class ChatMessage implements Message {

    private final ChatRoom chatRoom;
    private final Client sender;
    private final String content;

    public ChatMessage(ChatRoom chatRoom, Client sender, String content) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.content = content;
    }

    @Override
    public void send() throws IOException {
        for (Client client : chatRoom.getClients()) {
            if (client.getId() == sender.getId()) {
                continue;
            }
            DataOutputStream out = new DataOutputStream(client.getSocket().getOutputStream());
            MessageWriter messageWriter = new MessageWriter(out);
            messageWriter.write(sender.getName() + ": " + content);
        }
    }
}
