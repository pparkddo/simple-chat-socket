package project.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {
    
    private final int clientId;
    private final Socket socket;
    private final Map<Integer, ChatRoom> chatRooms;
    private final Channel channel;

    public ClientHandler(int clientId, Socket socket, Map<Integer, ChatRoom> chatRooms, Channel channel) {
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

            out.writeUTF(chatRooms.toString());

            int chatRoomId = in.readInt();
            if (chatRooms.containsKey(chatRoomId)) {
                chatRooms.get(chatRoomId).getClients().add(client);
            } else {
                ChatRoom chatRoom = new ChatRoom(chatRoomId);
                chatRooms.put(chatRoomId, chatRoom);
                chatRoom.getClients().add(client);
            }

            while (true) {
                String content = in.readUTF();
                Message message = new Message(chatRoomId, client.getId(), content);
                channel.send(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
