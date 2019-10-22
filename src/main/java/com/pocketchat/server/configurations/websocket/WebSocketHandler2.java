package com.pocketchat.server.configurations.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.thymeleaf.util.ObjectUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Working
public class WebSocketHandler2 extends TextWebSocketHandler {

    static List<WebSocketUser> webSocketUserList = new ArrayList<>();
    static List<String> messageList = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, InterruptedException {
        System.out.println("Text Message received: " + message.getPayload());
        System.out.println("message.getPayloadLength(): " + message.getPayloadLength());
        System.out.println("session.getId(): " + session.getId());
        System.out.println("session.getAcceptedProtocol(): " + session.getAcceptedProtocol());
        System.out.println("session.getBinaryMessageSizeLimit(): " + session.getBinaryMessageSizeLimit());
        System.out.println("session.getLocalAddress().getHostName(): " + session.getLocalAddress().getHostName());
        System.out.println("session.getLocalAddress().getHostString(): " + session.getLocalAddress().getHostString());
        System.out.println("session.getLocalAddress().getPort(): " + session.getLocalAddress().getPort());

        messageList.add(message.getPayload());

        messageList.forEach((String message2) -> {
            System.out.println("message2: " + message2);
        });

        WebSocketMessage webSocketMessage = convertToWebSocketMessage(message.getPayload());
        // TODO: Make sure frontend and backend object are same, so it can be converted properly.
        if (webSocketMessage != null) {
            session.sendMessage(message); // Send message back to frontend
//            session.sendMessage(new TextMessage("Very good."));
        }
        session.sendMessage(message);
    }

    WebSocketMessage convertToWebSocketMessage(String websockeMessageString) {
        WebSocketMessage webSocketMessage;
        try {
            webSocketMessage = objectMapper.readValue(websockeMessageString, WebSocketMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return webSocketMessage;
    }

}
