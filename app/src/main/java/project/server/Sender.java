package project.server;

import java.io.DataOutputStream;
import java.io.IOException;

public class Sender implements Runnable {

    private final Channel channel;

    public Sender(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            if (channel.getMessages().isEmpty()) {
                continue;
            }
            Message message = channel.getMessages().poll();
            Client sender = message.getSender();
            for (Client client : message.getChatRoom().getClients()) {
                try {
                    if (client.getId() == sender.getId()) {
                        continue;
                    }
                    DataOutputStream out = new DataOutputStream(client.getSocket().getOutputStream());
                    out.writeUTF(sender.getName() + ": " + message.getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
