package project.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class InputHandler implements Runnable {

    private final Socket socket;

    public InputHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            while (true) {
                System.out.println(in.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
