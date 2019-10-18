//package com.pocketchat.server.configurations.websocket;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.*;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws").withSockJS();
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        // This is the prefix before the frontend send a message.
//        // In frontend: /app/chat.addUser
//        // When a user joined a conversation:
//        // stompClient.send("/app/chat.addUser",
//        //        {},
//        //        JSON.stringify({sender: username, type: 'JOIN'})
//        //    )
//        // When send message:
//        // stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
//        registry.setApplicationDestinationPrefixes("/app");
//
//        // This is the prefix for the frontend WebSocket client to connect backend Websocket server.
//        // In frontend: stompClient.subscribe('/topic/public', ....);
////        registry.enableSimpleBroker("/topic");
//        registry.enableSimpleBroker("/topic");
//    }
//}
//
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.web.socket.config.annotation.EnableWebSocket;
////import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
////import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
////import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
////
////@Configuration
////@EnableWebSocket
////public class WebSocketConfig implements WebSocketConfigurer {
////
////    @Bean
////    public ServletServerContainerFactoryBean createWebSocketContainer() {
////        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
////        container.setMaxBinaryMessageBufferSize(1024000);
////        return container;
////    }
////
////    @Override
////    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
////        registry.addHandler(new WebSocketHandler(), "/socket").setAllowedOrigins("*");
////        registry.addHandler(new WebSocketHandler(), "/").setAllowedOrigins("*");
////    }
////}
