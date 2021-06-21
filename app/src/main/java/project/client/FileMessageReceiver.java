package project.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileMessageReceiver implements MessageReceiver {

    private final DataInputStream in;
    private final String downloadPath;

    public FileMessageReceiver(DataInputStream in, String downloadPath) {
        this.in = in;
        this.downloadPath = downloadPath;
    }

    @Override
    public void receive() throws IOException {
        String informMessage = in.readUTF();
        System.out.println(informMessage);
        String filename = in.readUTF();
        int fileSize = in.readInt();
        byte[] bytes = in.readNBytes(fileSize);
        Path filePath = Paths.get(downloadPath, filename);
        Files.write(filePath, bytes);
        System.out.println("파일이 정상적으로 수신되었습니다");
    }
}
