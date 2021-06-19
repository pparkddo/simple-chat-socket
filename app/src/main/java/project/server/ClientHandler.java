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
                out.writeUTF("아직 개설된 채팅방이 없습니다");
            } else {
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
                String chatContent = in.readUTF();
                Message message = new Message(chatRooms.get(chatRoomId), client, chatContent);
                channel.send(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
