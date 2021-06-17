package project.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Channel {
    
    private final Queue<Message> messages = new ConcurrentLinkedQueue<>();

    public Queue<Message> getMessages() {
        return messages;
    }

    public void send(Message message) {
        messages.add(message);
    }
}
