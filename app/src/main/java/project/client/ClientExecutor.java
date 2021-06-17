package project.client;

import java.io.IOException;
import java.net.Socket;

public class ClientExecutor {

    private final String host;
    private final int port;

    public ClientExecutor(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void execute() {
        try (Socket socket = new Socket(host, port)) {
            new Thread(new InputHandler(socket)).start();
            new OutputHandler(socket).run();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
