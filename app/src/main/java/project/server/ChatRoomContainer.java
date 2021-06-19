package project.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoomContainer {
    
    private final Map<String, ChatRoom> chatRooms = new ConcurrentHashMap<>();

    public ChatRoom get(String key) {
        return chatRooms.get(key);
    }

    public boolean containsKey(String key) {
        return chatRooms.containsKey(key);
    }

    public ChatRoom put(String key, ChatRoom chatRoom) {
        return chatRooms.put(key, chatRoom);
    }

    public boolean isEmpty() {
        return chatRooms.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : chatRooms.keySet()) {
            sb.append("채팅방 ").append(key).append(" 의 클라이언트").append("\n");
            sb.append("  - ");
            for (Client client : chatRooms.get(key).getClients()) {
                sb.append(client.getName()).append(", ");
            }
            sb.delete(sb.length()-2, sb.length()).append("\n");
        }
        return sb.toString().trim();
    }
}
