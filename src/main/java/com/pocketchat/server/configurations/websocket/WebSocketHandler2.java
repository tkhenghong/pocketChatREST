package com.pocketchat.server.configurations.websocket;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class WebSocketHandler2 extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("Text Message received: " + message.getPayload());
        session.sendMessage(message); // Send message back to frontend
        // ...
    }

}
