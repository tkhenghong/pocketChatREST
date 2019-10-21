package com.pocketchat.server.configurations.websocket;

import com.pocketchat.db.models.user.User;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketUser {
    WebSocketSession webSocketSession;
    User user;

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
