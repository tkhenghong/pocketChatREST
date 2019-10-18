package com.pocketchat.server.configurations.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
class WebSocketConfig2 implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/socket").setAllowedOrigins("*").withSockJS();
    }

    @Bean
    public WebSocketHandler2 myHandler() {
        return new WebSocketHandler2();
    }
}
