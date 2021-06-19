package project.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class OutputHandler implements Runnable {

    private final Socket socket;
    private final Scanner scanner;
    private static final String FILE_STRING = "file://";
    private static final String FILE_TYPE = "file";
    private static final String MESSAGE_TYPE = "message";

    public OutputHandler(Socket socket, Scanner scanner) {
        this.socket = socket;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            System.out.print("닉네임을 입력하세요: ");
            String name = scanner.nextLine();
            out.writeUTF(name);

            System.out.println("입장할 채팅방 이름을 입력하세요");
            String chatRoomId = scanner.nextLine();
            out.writeUTF(chatRoomId);

            while (true) {
                String content = scanner.nextLine();
                if (content.equals("exit")) {
                    break;
                }
                if (content.startsWith(FILE_STRING)) {
                    Path filename = Paths.get(content.substring(FILE_STRING.length()));
                    byte[] bytes = Files.readAllBytes(filename);
                    int fileSize = bytes.length;
                    out.writeUTF(FILE_TYPE);
                    out.writeUTF(filename.getFileName().toString());
                    out.writeInt(fileSize);
                    out.write(bytes);
                }
                else {
                    out.writeUTF(MESSAGE_TYPE);
                    out.writeUTF(content);
                }
                out.flush();
            }
            
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
