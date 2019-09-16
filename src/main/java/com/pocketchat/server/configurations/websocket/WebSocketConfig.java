package com.pocketchat.server.configurations.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@PropertySource("classpath:/application.properties") // @PropertyValue must get with @Value to work together
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${websocket.stomp.endpoint}")
    String webSocketEndPoint;

    @Value("${websocket.messageBroker.topicName}")
    String websocketMessageBrokerTopicName;


    // Create a endpoint for the Websocket
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        System.out.println("WebSocketConfig.java registerStompEndpoints()");
        System.out.println("WebSocketConfig.java  webSocketEndPoint: " + webSocketEndPoint);
        // IMPORTANT: When frontend user connects to Websocket, they need to mention this endpoint to be correctly connected.
        registry.addEndpoint(webSocketEndPoint).withSockJS();
        System.out.println("WebSocketConfig.java Registered Websocket endpoints.");
    }

    // Create a topic for the Websocket
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        System.out.println("WebSocketConfig.java configureMessageBroker()");
        System.out.println("WebSocketConfig.java  websocketMessageBrokerTopicName: " + websocketMessageBrokerTopicName);
        registry.enableSimpleBroker(websocketMessageBrokerTopicName);
        System.out.println("WebSocketConfig.java Registered WebSocket Message BrokerTopic Name.");
    }
}
