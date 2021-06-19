package project.server;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 클라이언트로 부터 전달된 메시지를 Sender 에게 전달해주기 위한 메시지큐 클래스
 */
public class Channel {
    
    /**
     * messages 를 Sender 에서 반복적으로 poll 해야하므로
     * BlockingQueue 인터페이스를 구현한 큐를 사용한다.
     */
    private final Queue<Message> messages = new LinkedBlockingQueue<>();

    public Queue<Message> getMessages() {
        return messages;
    }

    public void send(Message message) {
        messages.add(message);
    }
}
