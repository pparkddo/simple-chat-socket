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
            Content content = message.getContent();
            Client sender = message.getSender();
            for (Client client : message.getChatRoom().getClients()) {
                try {
                    if (client.getId() == sender.getId()) {
                        continue;
                    }
                    DataOutputStream out = new DataOutputStream(client.getSocket().getOutputStream());
                    if (content.getType().equals("file")) {
                        out.writeUTF(content.getType());
                        FileContent fileContent = (FileContent) content;
                        out.writeUTF(sender.getName() + "님이 파일을 전송하였습니다(" + fileContent.getFilename() + ")");
                        out.writeUTF(fileContent.getFilename());
                        out.writeInt(fileContent.getFileSize());
                        out.write(fileContent.getBytes());
                    }
                    else if (content.getType().equals("message")) {
                        out.writeUTF(content.getType());
                        MessageContent messageContent = (MessageContent) content;
                        out.writeUTF(sender.getName() + ": " + messageContent.getValue());
                    }
                    else {
                        throw new IllegalArgumentException("Unknown type: " + content.getType());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
