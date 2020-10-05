package com.pocketchat.server.configurations.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

// This is used to filter which type of SimpMessageType is allowed to connect with WebSocket. This is an authorization filter.
// https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/websocket.html#websocket-authorization
@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        // This is like WebSocket's Web Security.
        messages
                .nullDestMatcher().authenticated()
                .simpTypeMatchers(SimpMessageType.CONNECT).permitAll()
                .simpTypeMatchers(SimpMessageType.SUBSCRIBE, SimpMessageType.MESSAGE, SimpMessageType.UNSUBSCRIBE, SimpMessageType.DISCONNECT).authenticated()
                .anyMessage().denyAll();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
