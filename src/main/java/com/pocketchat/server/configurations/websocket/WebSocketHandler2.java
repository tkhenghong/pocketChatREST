package com.pocketchat.server.configurations.websocket;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Working
public class WebSocketHandler2 extends TextWebSocketHandler {

    static List<WebSocketUser> webSocketUserList = new ArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, InterruptedException {
        System.out.println("Text Message received: " + message.getPayload());
        session.sendMessage(message); // Send message back to frontend
        Thread.sleep(1000);
        session.sendMessage(new TextMessage("Very good."));
        // ...
    }

}
