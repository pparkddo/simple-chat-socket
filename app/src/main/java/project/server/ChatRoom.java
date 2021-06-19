package project.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 채팅방을 구현한 클래스
 * 채팅방 ID 와 해당 채팅방에 들어가있는 클라리언트들을 가진다.
 */
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
