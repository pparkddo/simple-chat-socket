package project.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class InputHandler implements Runnable {

    private final Socket socket;
    private final String downloadPath;
    private static final String FILE_TYPE = "file";
    private static final String MESSAGE_TYPE = "message";

    public InputHandler(Socket socket, String downloadPath) {
        this.socket = socket;
        this.downloadPath = downloadPath;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            while (true) {
                String type = in.readUTF();
                if (FILE_TYPE.equals(type)) {
                    String informMessage = in.readUTF();
                    String filename = in.readUTF();
                    int fileSize = in.readInt();
                    byte[] bytes = in.readNBytes(fileSize);
                    Path filePath = Paths.get(downloadPath, filename);
                    Files.write(filePath, bytes);
                    System.out.println(informMessage);
                }
                else if (MESSAGE_TYPE.equals(type)) {
                    System.out.println(in.readUTF());
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
