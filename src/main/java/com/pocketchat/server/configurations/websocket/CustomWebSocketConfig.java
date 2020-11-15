package com.pocketchat.server.configurations.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.services.user_contact.UserContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class CustomWebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserContactService userContactService;

    private final WebSocketSessionManager webSocketSessionManager;

    private final WebSocketMessageSender webSocketMessageSender;

    private final WebsocketChannelInterceptor websocketChannelInterceptor;

    private final ObjectMapper objectMapper;

    @Autowired
    CustomWebSocketConfig(UserContactService userContactService,
                          WebSocketSessionManager webSocketSessionManager,
                          WebSocketMessageSender webSocketMessageSender,
                          WebsocketChannelInterceptor websocketChannelInterceptor,
                          ObjectMapper objectMapper) {
        this.userContactService = userContactService;
        this.webSocketSessionManager = webSocketSessionManager;
        this.webSocketMessageSender = webSocketMessageSender;
        this.websocketChannelInterceptor = websocketChannelInterceptor;
        this.objectMapper = objectMapper;
    }

    // STOMP over WebSocket
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // Listen to STOMP client topic
        registry.setApplicationDestinationPrefixes("/app");
    }

    // Websocket
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(websocketChannelInterceptor);
    }

    // TODO: How WL knows this and used this to add WebSocket user sessions?
    // https://www.programcreek.com/java-api-examples/?api=org.springframework.web.socket.handler.WebSocketHandlerDecorator
    // Example 7
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandlerDecorator decorate(WebSocketHandler handler) {
                return new WebSocketHandlerDecorator(handler) {

                    // NOTE: Unauthenticated/authenticated users will trigger this method.
                    @Override
                    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) session.getPrincipal();
                        if (!ObjectUtils.isEmpty(usernamePasswordAuthenticationToken)) {
                            setSecurityContext(usernamePasswordAuthenticationToken);
                            logger.info("Incoming connection for {}.", usernamePasswordAuthenticationToken.getName());
                            UserContact ownUserContact = userContactService.getOwnUserContact();

                            webSocketSessionManager.addWebSocketSession(ownUserContact.getId(), session);
                            List<WebSocketSession> webSocketSessionList = webSocketSessionManager.getWebSocketSessions(ownUserContact.getId());
                            webSocketMessageSender.sendMessage(ownUserContact.getId(), webSocketSessionList);
                        }

                        super.afterConnectionEstablished(session);
                    }

                    @Override
                    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) session.getPrincipal();
                        if (!ObjectUtils.isEmpty(usernamePasswordAuthenticationToken)) {
                            setSecurityContext(usernamePasswordAuthenticationToken);
                            logger.info("Closing connection for {}.", usernamePasswordAuthenticationToken.getName());

                            UserContact ownUserContact = userContactService.getOwnUserContact();

                            webSocketSessionManager.removeWebSocketSession(ownUserContact.getId(), session);
                        }

                        super.afterConnectionClosed(session, closeStatus);
                    }

                    void readUsernamePasswordAuthenticationToken(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
                        try {
                            String usernamePasswordAuthenticationTokenString = objectMapper.writeValueAsString(usernamePasswordAuthenticationToken);
                            logger.info("usernamePasswordAuthenticationTokenString: {}", usernamePasswordAuthenticationTokenString);
                        } catch (JsonProcessingException jsonProcessingException) {
                            logger.error("Unable to parse usernamePasswordAuthenticationToken into JSON string.");
                        }
                    }

                    void setSecurityContext(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
                        readUsernamePasswordAuthenticationToken(usernamePasswordAuthenticationToken);
                        // Straight set Authentication token into Spring Security Context, because the content is correct and already passed through JWT filter.
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                };
            }
        });
    }

    @Bean
    public CustomHandshakeInterceptor httpSessionIdHandshakeInterceptor() {
        return new CustomHandshakeInterceptor();
    }
}
