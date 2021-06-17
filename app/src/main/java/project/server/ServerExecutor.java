package project.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerExecutor {

    private int clientId = 0;
    private final int port;
    private final Map<Integer, ChatRoom> chatRooms = new ConcurrentHashMap<>();
    private final Channel channel = new Channel();
    private final Sender sender = new Sender(channel, chatRooms);

    public ServerExecutor(int port) {
        this.port = port;
    }

    public void execute() {
        new Thread(sender).start();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("채팅 서버가 포트 " + port + " 번에서 실행 중입니다");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("새로운 클라이언트가 접속했습니다");
                ClientHandler handler = new ClientHandler(clientId++, socket, chatRooms, channel);
                new Thread(handler).start();
            }
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
