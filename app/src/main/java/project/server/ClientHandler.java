package project.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 클라이언트의 입력을 처리하고 출력을 만들어 Channel 로 전달하는 클래스
 */
public class ClientHandler implements Runnable {
    
    private final int clientId;
    private final Socket socket;
    private final ChatRoomContainer chatRooms;
    private final Channel channel;
    private static final String FILE_TYPE = "file";
    private static final String CHAT_TYPE = "message";

    public ClientHandler(int clientId, Socket socket, ChatRoomContainer chatRooms, Channel channel) {
        this.clientId = clientId;
        this.socket = socket;
        this.chatRooms = chatRooms;
        this.channel = channel;
    }

    /**
     * 클라이언트가 접속하면 클라이언트의 닉네임을 입력받아 Client 객체를 생성한다.
     * 현재 생성된 모든 채팅방의 정보를 클라이언트에게 송신한다.
     * 클라이언트가 들어갈 채팅방 ID 를 보내고
     * 채팅방 ID 가 이미 있다면 그 채팅방에 클라이언트를 추가,
     * 채팅방 ID 가 없다면 새로운 채팅방을 개설한다.
     * 이후로는 클라이언트로 부터 메시지 또는 파일을 전송받는다.
     * type 을 먼저 입력받고 type 이 file 이라면 파일처리 로직을 실행하고
     * type 이 message 라면 메시지처리 로직을 실행한다.
     * 올바르지 않은 type 이라면 IllegalArgumentException 을 발생시킨다.
     * 각 처리로직에 맞춰 생성한 Message 객체를 Channel 을 통해 Sender 에게 전송한다.
     */
    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream())){
            MessageWriter messageWriter = new MessageWriter(out);
            String clientName = in.readUTF();
            Client client = new Client(clientId, clientName, socket);

            if (chatRooms.isEmpty()) {
                messageWriter.write("아직 개설된 채팅방이 없습니다");
            } else {
                messageWriter.write(chatRooms.toString());
            }

            String chatRoomId = in.readUTF();
            if (chatRooms.containsKey(chatRoomId)) {
                joinChatRoom(chatRoomId, client);
            } else {
                makeChatRoom(chatRoomId, client);
            }

            while (true) {
                String type = in.readUTF();
                Message message = parseMessage(type, in, chatRoomId, client);
                channel.send(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void joinChatRoom(String chatRoomId, Client client) {
        chatRooms.get(chatRoomId).getClients().add(client);
    }

    private void makeChatRoom(String chatRoomId, Client client) {
        ChatRoom chatRoom = new ChatRoom(chatRoomId);
        chatRooms.put(chatRoomId, chatRoom);
        chatRoom.getClients().add(client);
    }

    private Message parseMessage(String type, DataInputStream in, String chatRoomId, Client client) throws IOException {
        if (FILE_TYPE.equals(type)) {
            return parseFileMessage(in, chatRoomId, client);
        }
        if (CHAT_TYPE.equals(type)) {
            return parseChatMessage(in, chatRoomId, client);
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }

    private Message parseFileMessage(DataInputStream in, String chatRoomId, Client client) throws IOException {
        String filename = in.readUTF();
        int fileSize = in.readInt();
        byte[] bytes = in.readNBytes(fileSize);
        return new FileMessage(chatRooms.get(chatRoomId), client, filename, bytes, client.getName());
    }

    private Message parseChatMessage(DataInputStream in, String chatRoomId, Client client) throws IOException {
        String content = in.readUTF();
        return new ChatMessage(chatRooms.get(chatRoomId), client, content);
    }
}
