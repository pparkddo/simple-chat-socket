package project.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * 클라이언트 소켓을 실행하는 클래스
 */
public class ClientExecutor {

    private final String host;
    private final int port;

    public ClientExecutor(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 생성자를 통해 주입된 host 와 port 로 Socket 을 생성한다.
     * 파일 수신시의 다운로드 폴더를 사용자 입력으로 지정한다.
     * 서버로부터 입력을 처리하는 InputHandler 쓰레드는 별도의 쓰레드로 생성하여 실행하고
     * 사용자로부터 출력을 처리하는 OutputHandler 는 메인쓰레드에서 실행한다.
     */
    public void execute() {
        try (Socket socket = new Socket(host, port);
            Scanner scanner = new Scanner(System.in)) {
            System.out.println(getDownloadPathSettingMessage());
            String downloadPath = getDownloadPath(scanner);
            System.out.println(getDownloadPathInformMessage(downloadPath));
            new Thread(new InputHandler(socket, downloadPath)).start();
            new OutputHandler(socket, scanner).run();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDownloadPath(Scanner scanner) {
        String downloadPath = scanner.nextLine();
        if (downloadPath.isBlank()) {
            downloadPath = getUserHomePath();
        }
        return downloadPath;
    }

    private String getDownloadPathSettingMessage() {
        StringBuilder message = new StringBuilder();
        message.append("파일 수신시 다운로드 할 위치를 지정해주세요.");
        message.append("미지정시 바탕화면에 다운로드됩니다").append("\n");
        message.append("  ex) C:\\Users\\Me\\Desktop").append("\n");
        message.append("파일 다운로드 경로 입력(미지정하려면 엔터): ");
        return message.toString();
    }

    private String getDownloadPathInformMessage(String downloadPath) {
        return "다운로드할 경로: " + downloadPath;
    }

    private String getUserHomePath() {
        return System.getProperty("user.home") + "\\Desktop";
    }
}
