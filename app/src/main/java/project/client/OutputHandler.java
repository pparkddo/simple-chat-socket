package project.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * 사용자의 출력을 처리하는 클래스
 */
public class OutputHandler implements Runnable {

    private final Socket socket;
    private final Scanner scanner;
    private static final String FILE_STRING = "file://";
    private static final String FILE_TYPE = "file";
    private static final String CHAT_TYPE = "message";

    public OutputHandler(Socket socket, Scanner scanner) {
        this.socket = socket;
        this.scanner = scanner;
    }

    /**
     * 닉네임, 채팅방 설정을 한다.
     * 이후로는 사용자로부터 채팅을 입력받는다.
     * 채팅내용이 file:// 로 시작할 경우는 파일로 인식하여 파일전송 로직을 실행한다.
     * file://c:\Document\sample.doc 과 같이 입력하면
     * 해당 파일을 같은 채팅방 내 모든 클라이언트에게 전송한다.
     */
    @Override
    public void run() {
        try (DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
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
                    writeFileMessage(out, filename.getFileName().toString(), bytes);
                }
                else {
                    writeChatMessage(out, content);
                }
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFileMessage(
        DataOutputStream out, String filename, byte[] bytes) throws IOException {
        out.writeUTF(FILE_TYPE);
        out.writeUTF(filename);
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private void writeChatMessage(DataOutputStream out, String content) throws IOException {
        out.writeUTF(CHAT_TYPE);
        out.writeUTF(content);
    }
}
