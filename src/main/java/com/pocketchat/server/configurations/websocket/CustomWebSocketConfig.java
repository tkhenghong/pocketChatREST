package com.pocketchat.server.configurations.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

@Configuration
@EnableWebSocketMessageBroker
public class CustomWebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final WebSocketSessionManager webSocketSessionManager;

    @Autowired
    CustomWebSocketConfig(WebSocketSessionManager webSocketSessionManager) {
        this.webSocketSessionManager = webSocketSessionManager;
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
        registration.interceptors(new WebsocketChannelInterceptor());
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
                        if (StringUtils.hasText(usernamePasswordAuthenticationToken.getName())) {
                            logger.info("Incoming connection for usernamePasswordAuthenticationToken.getName(): {}", usernamePasswordAuthenticationToken.getName());
                        }

                        webSocketSessionManager.addWebSocketSession(usernamePasswordAuthenticationToken.getName(), session);
                        // TODO: Send messages through Websocket
                        super.afterConnectionEstablished(session);
                    }

                    @Override
                    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) session.getPrincipal();
                        if (StringUtils.hasText(usernamePasswordAuthenticationToken.getName())) {
                            logger.info("Closing connection for usernamePasswordAuthenticationToken.getName(): {}", usernamePasswordAuthenticationToken.getName());
                        }

                        webSocketSessionManager.removeWebSocketSession(usernamePasswordAuthenticationToken.getName(), session);
                        super.afterConnectionClosed(session, closeStatus);
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
