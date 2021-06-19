package project.server;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Channel 로 부터 전달된 Message 를 채팅방에 들어가 있는 클라이언트들에게 전송하는 클래스
 */
public class Sender implements Runnable {

    private final Channel channel;

    public Sender(Channel channel) {
        this.channel = channel;
    }

    /**
     * Channel 이 비어있지 않다면 반복적으로 Message 를 들고온다.
     * 들고온 Message 에 있는 채팅방 정보를 활용하여
     * 채팅방에 있는 모든 클라이언트들에게 메시지를 전송한다.
     * 메시지를 보낸 클라이언트 자신에게는 메시지를 전달하지 않는다.
     */
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
