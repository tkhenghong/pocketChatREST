package com.pocketchat.server.configurations.websocket.custom_configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocketchat.server.configurations.websocket.WebSocketMessageSender;
import com.pocketchat.server.configurations.websocket.WebSocketSessionManager;
import com.pocketchat.server.configurations.websocket.handlers.WebSocketCustomHandler;
import com.pocketchat.services.user_contact.UserContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.List;

/**
 * This is a class for create WebSocket broker that similar to WebSocketConfig class but with much simpler configurations.
 * NOTE: You can run this class same with WebSocketMessageBrokerConfigurerCustomConfig class.
 * NOTE: The paths below can't be shared with the ones in WebSocketMessageBrokerConfigurerCustomConfig class unless .
 */
@Configuration
@EnableWebSocket
public class WebSocketConfigurerCustomConfig implements WebSocketConfigurer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final List<String> webSocketBrokerEndpointList;

    private final List<String> webSocketBrokerAllowedOriginList;

    private final WebSocketSessionManager webSocketSessionManager;

    private final WebSocketMessageSender webSocketMessageSender;

    private final UserContactService userContactService;

    private final ObjectMapper objectMapper;

    @Autowired
    public WebSocketConfigurerCustomConfig(@Value("#{'${websocket.broker.endpoint.list}'.split(',')}") List<String> webSocketBrokerEndpointList,
                                           @Value("#{'${websocket.broker.allowed.origin.list}'.split(',')}") List<String> webSocketBrokerAllowedOriginList,
                                           WebSocketSessionManager webSocketSessionManager,
                                           WebSocketMessageSender webSocketMessageSender,
                                           UserContactService userContactService,
                                           ObjectMapper objectMapper) {
        this.webSocketBrokerEndpointList = webSocketBrokerEndpointList;
        this.webSocketBrokerAllowedOriginList = webSocketBrokerAllowedOriginList;
        this.webSocketSessionManager = webSocketSessionManager;
        this.webSocketMessageSender = webSocketMessageSender;
        this.userContactService = userContactService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), webSocketBrokerEndpointList.toArray(new String[0])).setAllowedOrigins(webSocketBrokerAllowedOriginList.toArray(new String[0])).withSockJS();
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new WebSocketCustomHandler(webSocketSessionManager, webSocketMessageSender, userContactService, objectMapper);
    }
}
