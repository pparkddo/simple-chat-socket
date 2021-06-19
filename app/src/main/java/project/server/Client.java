package project.server;

import java.net.Socket;

/**
 * 서버에서 Client 들을 관리하기 위한 클래스
 * 클라이언트의 ID, 닉네임, 클라이언트소켓을 가진다.
 */
public class Client {

    private final int id;
    private final String name;
    private final Socket socket;

    public Client(int id, String name, Socket socket) {
        this.id = id;
        this.name = name;
        this.socket = socket;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }
}
