package com.pocketchat.server.configurations.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class WebSocketSessionManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Map<String, List<WebSocketSession>> onlineWebsocketSessions = new ConcurrentHashMap<>();

    public void addWebSocketSession(String userId, WebSocketSession webSocketSession) {
        logger.info("userId: {}", userId);
        logger.info("webSocketSession.getId(): {}", webSocketSession.getId());
        List<WebSocketSession> existingWebSocketSessions = getWebSocketSessions(userId);

        if (ObjectUtils.isEmpty(existingWebSocketSessions)) {
            existingWebSocketSessions = new ArrayList<>();
            existingWebSocketSessions.add(webSocketSession);
        } else {
            // Find user WebSocket sessions.
            List<WebSocketSession> resultWebSocketSession = existingWebSocketSessions.stream().filter(webSocketSession1 -> webSocketSession.getId().equals(webSocketSession1.getId())).collect(Collectors.toList());

            if (resultWebSocketSession.size() == 1) {
                logger.error("Error: Duplicate Websocket Session, not adding. ID: {}", webSocketSession.getId());
            }
        }

        onlineWebsocketSessions.put(userId, existingWebSocketSessions);
        logger.info("onlineWebsocketSessions.size(): {}", onlineWebsocketSessions.size());
    }

    public void removeWebSocketSession(String userId, WebSocketSession webSocketSession) {
        List<WebSocketSession> existingWebSocketSessions = getWebSocketSessions(userId);

        if (!ObjectUtils.isEmpty(existingWebSocketSessions)) {
            Optional<WebSocketSession> webSocketSessionOptional = existingWebSocketSessions.stream().filter(existingWebSocketSession -> existingWebSocketSession.getId().equals(webSocketSession.getId())).findAny();

            if (webSocketSessionOptional.isPresent()) {
                // Close WebSocket session on this end.
                try {
                    webSocketSessionOptional.get().close();
                } catch (IOException ioException) {
                    logger.error("Unable to close Websocket manually. ID: {}", webSocketSessionOptional.get().getId());
                }

                // Remove WebSocket session
                existingWebSocketSessions.removeIf(webSocketSession1 -> webSocketSession1.getId().equals(webSocketSession.getId()));
            }
        } else {
            logger.error("Unable to remove websocket user session WebSocket session ID: {} as user doesn't even have any exising Websocket sessions.", webSocketSession.getId());
        }

        onlineWebsocketSessions.put(userId, existingWebSocketSessions);
        logger.info("onlineWebsocketSessions.size(): {}", onlineWebsocketSessions.size());
    }


    /**
     * Get a list of WebSocket sessions based on the user
     * @param userId
     * @return
     */
    public List<WebSocketSession> getWebSocketSessions(String userId) {
        return onlineWebsocketSessions.get(userId);
    }
}
