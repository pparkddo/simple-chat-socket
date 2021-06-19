package project.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 서버 소켓을 실행하는 클래스
 */
public class ServerExecutor {

    private int clientId = 0;
    private final int port;
    private final ChatRoomContainer chatRooms = new ChatRoomContainer();
    private final Channel channel = new Channel();
    private final Sender sender = new Sender(channel);

    public ServerExecutor(int port) {
        this.port = port;
    }

    /**
     * Sender 쓰레드를 실행하고
     * 주어진 port 로 서버소켓을 생성한다.
     * 매번 새로운 클라이언트가 접속할 때마다 ClientHandler 를 생성하여 새 쓰레드에서 실행한다.
     */
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
