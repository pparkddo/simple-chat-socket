package project.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class OutputHandler implements Runnable {

    private final Socket socket;

    public OutputHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            System.out.print("닉네임을 입력하세요: ");
            String name = scanner.nextLine();
            out.writeUTF(name);

            System.out.println("입력할 채팅방 이름을 입력하세요");
            String chatRoomId = scanner.nextLine();
            out.writeUTF(chatRoomId);

            while (true) {
                String chatContent = scanner.nextLine();
                out.writeUTF(chatContent);
                out.flush();
                if (chatContent.equals("exit")) {
                    break;
                }
            }
            
            out.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
