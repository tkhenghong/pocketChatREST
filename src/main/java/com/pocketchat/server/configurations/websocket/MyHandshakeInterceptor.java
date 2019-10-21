package com.pocketchat.server.configurations.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class MyHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("MyHandshakeInterceptor.java beforeHandshake()");
        System.out.println("request: " + request.toString());
        System.out.println("response: " + response.toString());
        System.out.println("wsHandler: " + wsHandler.toString());
        System.out.println("attributes: " + attributes.toString());

        // Return true to proceed handshake
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println("MyHandshakeInterceptor.java afterHandshake()");
    }
}
