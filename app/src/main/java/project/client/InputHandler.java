package project.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 서버로부터 수신되는 입력을 처리하는 클래스
 */
public class InputHandler implements Runnable {

    private final Socket socket;
    private final String downloadPath;

    public InputHandler(Socket socket, String downloadPath) {
        this.socket = socket;
        this.downloadPath = downloadPath;
    }

    /**
     * 먼저 type 을 수신한다.
     * type 이 file 일 때는 파일 데이터를 처리하는 로직을 실행한다.
     * type 이 message 일 때는 메시지 데이터를 처리하는 로직을 실행한다.
     * 만약 올바르지 않은 type 이라면 IllegalArgumentException 을 일으킨다.
     */
    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
            MessageReceiverFactory messageReceiverFactory = new MessageReceiverFactory(in, downloadPath);
            while (true) {
                String type = receiveType(in);
                MessageReceiver receiver = messageReceiverFactory.get(type);
                receiver.receive();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String receiveType(DataInputStream in) throws IOException {
        return in.readUTF();
    }
}
