package project.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChatRoom {

    private final String id;
    private final Queue<Client> clients = new ConcurrentLinkedQueue<>();

    public ChatRoom(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public Queue<Client> getClients() {
        return this.clients;
    }
}
