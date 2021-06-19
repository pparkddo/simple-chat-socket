package project.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientExecutor {

    private final String host;
    private final int port;

    public ClientExecutor(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void execute() {
        try (Socket socket = new Socket(host, port)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("파일 수신시 다운로드 할 위치를 지정해주세요. 미지정시 바탕화면에 다운로드됩니다.");
            System.out.println("  ex) C:\\Users\\Me\\Desktop");
            System.out.print("파일 다운로드 경로 입력(미지정하려면 엔터): ");
            String downloadPath = scanner.nextLine();
            if (downloadPath.isBlank()) {
                downloadPath = System.getProperty("user.home") + "\\Desktop";
            }
            System.out.println("다운로드할 경로: " + downloadPath);
            new Thread(new InputHandler(socket, downloadPath)).start();
            new OutputHandler(socket, scanner).run();
            scanner.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
