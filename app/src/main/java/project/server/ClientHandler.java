package project.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    
    private final int clientId;
    private final Socket socket;
    private final ChatRoomContainer chatRooms;
    private final Channel channel;
    private static final String FILE_TYPE = "file";
    private static final String MESSAGE_TYPE = "message";

    public ClientHandler(int clientId, Socket socket, ChatRoomContainer chatRooms, Channel channel) {
        this.clientId = clientId;
        this.socket = socket;
        this.chatRooms = chatRooms;
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String clientName = in.readUTF();
            Client client = new Client(clientId, clientName, socket);

            if (chatRooms.isEmpty()) {
                out.writeUTF(MESSAGE_TYPE);
                out.writeUTF("아직 개설된 채팅방이 없습니다");
            } else {
                out.writeUTF(MESSAGE_TYPE);
                out.writeUTF(chatRooms.toString());
            }

            String chatRoomId = in.readUTF();
            if (chatRooms.containsKey(chatRoomId)) {
                chatRooms.get(chatRoomId).getClients().add(client);
            } else {
                ChatRoom chatRoom = new ChatRoom(chatRoomId);
                chatRooms.put(chatRoomId, chatRoom);
                chatRoom.getClients().add(client);
            }

            while (true) {
                String type = in.readUTF();
                if (FILE_TYPE.equals(type)) {
                    String filename = in.readUTF();
                    int fileSize = in.readInt();
                    byte[] bytes = in.readNBytes(fileSize);
                    Content content = new FileContent(filename, fileSize, bytes);
                    Message message = new Message(chatRooms.get(chatRoomId), client, content);
                    channel.send(message);
                }
                else if (MESSAGE_TYPE.equals(type)) {
                    String value = in.readUTF();
                    Content content = new MessageContent(value);
                    Message message = new Message(chatRooms.get(chatRoomId), client, content);
                    channel.send(message);
                }
                else {
                    throw new IllegalArgumentException("Unknown type: " + type);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
