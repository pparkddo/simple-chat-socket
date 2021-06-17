package project.server;

import java.net.Socket;

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
