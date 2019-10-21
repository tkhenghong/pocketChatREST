package com.pocketchat.server.configurations.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig3 implements WebSocketMessageBrokerConfigurer {


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").setAllowedOrigins("*");
    }


    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        System.out.println("WebSocketConfig3.java handleSessionConnected()");
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        System.out.println("WebSocketConfig3.java handleSessionDisconnect()");
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                System.out.println("WebSocketConfig3.java preSend()");
                System.out.println("WebSocketConfig3.java message: " + message.toString());
                System.out.println("WebSocketConfig3.java channel: " + channel.toString());
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                List<String> authorizationList = accessor.getNativeHeader("Authorization");

                String authorization = !CollectionUtils.isEmpty(authorizationList) ? authorizationList.stream().findFirst().orElse(null) : null;

                System.out.println("authorization: " + authorization);
                System.out.println("accessor.getMessageType(): " + accessor.getMessageType());

//                return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());

                return message;
            }

            @Override
            public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
                System.out.println("WebSocketConfig3.java postSend()");
                System.out.println("WebSocketConfig3.java message: " + message.toString());
                System.out.println("WebSocketConfig3.java channel: " + channel.toString());
            }

            @Override
            public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex) {
                System.out.println("WebSocketConfig3.java afterSendCompletion()");
                System.out.println("WebSocketConfig3.java message: " + message.toString());
                System.out.println("WebSocketConfig3.java channel: " + channel.toString());
            }

            @Override
            public boolean preReceive(MessageChannel channel) {
                System.out.println("WebSocketConfig3.java preReceive()");
                System.out.println("WebSocketConfig3.java channel: " + channel.toString());

                return true;
            }

            @Override
            public Message<?> postReceive(Message<?> message, MessageChannel channel) {
                System.out.println("WebSocketConfig3.java postReceive()");
                System.out.println("WebSocketConfig3.java message: " + message.toString());
                return message;
            }

            @Override
            public void afterReceiveCompletion(@Nullable Message<?> message, MessageChannel channel, @Nullable Exception ex) {
                System.out.println("WebSocketConfig3.java afterReceiveCompletion()");
                System.out.println("WebSocketConfig3.java message: " + message.toString());
                System.out.println("WebSocketConfig3.java channel: " + channel.toString());
            }
        });
    }

//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(new WebSocketHandler2(), "/myHandler")
//                .addInterceptors(new HttpSessionHandshakeInterceptor() {
//                    @Override
//                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//                        System.out.println("WebSocketConfig3.java beforeHandshake()");
//                        System.out.println("WebSocketConfig3.java request: " + request.toString());
//                        System.out.println("WebSocketConfig3.java response: " + response.toString());
//                        System.out.println("WebSocketConfig3.java wsHandler: " + wsHandler.toString());
//
//                        return true;
//                    }
//
//                    @Override
//                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                                               WebSocketHandler wsHandler, @Nullable Exception ex) {
//                        System.out.println("WebSocketConfig3.java afterHandshake()");
//                        System.out.println("WebSocketConfig3.java request: " + request.toString());
//                        System.out.println("WebSocketConfig3.java response: " + response.toString());
//                        System.out.println("WebSocketConfig3.java wsHandler: " + wsHandler.toString());
//                    }
//                });
//    }
}