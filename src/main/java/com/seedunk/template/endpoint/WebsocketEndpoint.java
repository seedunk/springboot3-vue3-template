package  com.seedunk.template.endpoint;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
 
@Component
@ServerEndpoint("/endpoint/websocket")
public class WebsocketEndpoint {

    // 存储所有在线会话 (sessionId -> Session)
    private static final ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        
        /**[获取客户端连接URL参数](#marker)**/
        Map<String, List<String>> requestParams = session.getRequestParameterMap();
        sessions.put(session.getId(), session);
 
        //System.out.println("WebSocket 连接成功，当前在线: " + sessions.size());
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session.getId());
        //System.out.println("WebSocket 连接断开，当前在线: " + sessions.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 收到客户端消息（可选），这里只做日志
        //System.out.println("收到客户端消息: " + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 推送通知给所有在线客户端
     */
    public void pushToAll(String message) {
        for (Session session : sessions.values()) {
            try {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 推送通知给指定用户（需要客户端注册 userId）
     */
    public void pushToUser(String userId, String message) {
        // 这里可以根据 userId 找到对应的 Session 发送
        // 需要客户端连接时传入 userId，或维护 userId -> Session 映射
    }

    /**
     * 获取当前在线人数
     */
    public int getOnlineCount() {
        return sessions.size();
    }
}