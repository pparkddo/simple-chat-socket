package project.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class Sender implements Runnable {

    private final Channel channel;
    private final Map<Integer, ChatRoom> chatRooms;

    public Sender(Channel channel, Map<Integer, ChatRoom> chatRooms) {
        this.channel = channel;
        this.chatRooms = chatRooms;
    }

    @Override
    public void run() {
        while (true) {
            if (channel.getMessages().isEmpty()) {
                continue;
            }
            Message message = channel.getMessages().poll();
            for (Client client : chatRooms.get(message.getChatRoomId()).getClients()) {
                try {
                    DataOutputStream out = new DataOutputStream(client.getSocket().getOutputStream());;
                    out.writeUTF(message.getSenderId() + ": " + message.getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
