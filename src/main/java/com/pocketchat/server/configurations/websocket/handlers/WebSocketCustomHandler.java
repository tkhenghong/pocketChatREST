package com.pocketchat.server.configurations.websocket.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.server.configurations.websocket.WebSocketMessageSender;
import com.pocketchat.server.configurations.websocket.WebSocketSessionManager;
import com.pocketchat.services.user_contact.UserContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

/**
 * This is a class that is used to handle all override events below.
 * You may add "extends TextWebSocketHandler" after the WebSocketCustomHandler(this), although this is .
 * Reference: https://docs.spring.io/spring-framework/docs/4.1.7.RELEASE/spring-framework-reference/html/websocket.html#websocket-server-handler
 */
public class WebSocketCustomHandler implements WebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WebSocketSessionManager webSocketSessionManager;

    private final WebSocketMessageSender webSocketMessageSender;

    private final UserContactService userContactService;

    private final ObjectMapper objectMapper;

    public WebSocketCustomHandler(WebSocketSessionManager webSocketSessionManager, WebSocketMessageSender webSocketMessageSender, UserContactService userContactService, ObjectMapper objectMapper) {
        this.webSocketSessionManager = webSocketSessionManager;
        this.webSocketMessageSender = webSocketMessageSender;
        this.userContactService = userContactService;
        this.objectMapper = objectMapper;
    }

    /**
     * Event after the frontend client has connected the server successfully.
     *
     * @param session: WebSocketSession object that contains the info about the frontend client.
     * @throws Exception Any possible exceptions related from unable to send messages to WebSocketSession object, to parsing error and etc.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("afterConnectionEstablished()");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) session.getPrincipal();
        if (!ObjectUtils.isEmpty(usernamePasswordAuthenticationToken)) {
            setSecurityContext(usernamePasswordAuthenticationToken);
            logger.info("Incoming connection for {}.", usernamePasswordAuthenticationToken.getName());
            UserContact ownUserContact = userContactService.getOwnUserContact();

            webSocketSessionManager.addWebSocketSession(ownUserContact.getId(), session);
            List<WebSocketSession> webSocketSessionList = webSocketSessionManager.getWebSocketSessions(ownUserContact.getId());
            webSocketMessageSender.sendMessage(ownUserContact.getId(), webSocketSessionList);
        }
    }

    /**
     * Event when a message is fired from/to the frontend client.
     *
     * @param session: WebSocketSession object that contains the info about the frontend client.
     * @param message: The message retreived from the frontend clients.
     * @throws Exception Any possible exceptions related from unable to send messages to WebSocketSession object, to parsing error and etc.
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        logger.info("handleMessage()");
        session.sendMessage(message); // Send back to the session.
    }

    /**
     * Event when any error happens within the WebSocket connections.
     *
     * @param session:   WebSocketSession object that contains the info about the frontend client.
     * @param exception: Exception object's throwable which may contain details of the exception.
     * @throws Exception Any possible exceptions related from unable to send messages to WebSocketSession object, to parsing error and etc.
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.info("handleTransportError()");
        logger.info("Exception message: ", exception.getMessage());
    }

    /**
     * Event when a WebSocket session is closed by the frontend client or server itself.
     *
     * @param session:     WebSocketSession object that contains the info about the frontend client.
     * @param closeStatus: A CloseStatus object that contains the reason of why the WebSocket session is closed by frontend client or server itself.
     * @throws Exception Any possible exceptions related from unable to send messages to WebSocketSession object, to parsing error and etc.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.info("afterConnectionClosed()");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) session.getPrincipal();
        if (!ObjectUtils.isEmpty(usernamePasswordAuthenticationToken)) {
            setSecurityContext(usernamePasswordAuthenticationToken);
            logger.info("Closing connection for {}.", usernamePasswordAuthenticationToken.getName());

            UserContact ownUserContact = userContactService.getOwnUserContact();

            webSocketSessionManager.removeWebSocketSession(ownUserContact.getId(), session);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    private void readUsernamePasswordAuthenticationToken(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        try {
            String usernamePasswordAuthenticationTokenString = objectMapper.writeValueAsString(usernamePasswordAuthenticationToken);
            logger.info("usernamePasswordAuthenticationTokenString: {}", usernamePasswordAuthenticationTokenString);
        } catch (JsonProcessingException jsonProcessingException) {
            logger.error("Unable to parse usernamePasswordAuthenticationToken into JSON string.");
        }
    }

    private void setSecurityContext(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        readUsernamePasswordAuthenticationToken(usernamePasswordAuthenticationToken);
        // Straight set Authentication token into Spring Security Context, because the content is correct and already passed through JWT filter.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
