package project.client;

import java.io.DataInputStream;
import java.io.IOException;

public class ChatMessageReceiver implements MessageReceiver {

    private final DataInputStream in;

    public ChatMessageReceiver(DataInputStream in) {
        this.in = in;
    }

    @Override
    public void receive() throws IOException {
        System.out.println(in.readUTF());
    }
}
