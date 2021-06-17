package project.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChatRoom {

    private final int id;
    private final Queue<Client> clients = new ConcurrentLinkedQueue<>();

    public ChatRoom(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public Queue<Client> getClients() {
        return this.clients;
    }
}
