package com.pocketchat.server.configurations.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class CustomHandshakeInterceptor implements HandshakeInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        logger.info("CustomHandshakeInterceptor beforeHandshake()");
        logger.info("request.getLocalAddress().getHostName(): {}", request.getLocalAddress().getHostName());
        logger.info("request.getLocalAddress().getHostString(): {}", request.getLocalAddress().getHostString());
        logger.info("request.getLocalAddress().getPort(): {}", request.getLocalAddress().getPort());
        logger.info("request.getPrincipal().getName(): {}", !ObjectUtils.isEmpty(request.getPrincipal()) ? request.getPrincipal().getName() : "Empty principal name.");
        logger.info("attributes list");
        attributes.forEach((String key, Object value) -> {
            logger.info("key: {}", key);
            logger.info("value: {}", value);
        });
        logger.info("attributes list ENDED");

        // Return true to proceed handshake

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        logger.info("CustomHandshakeInterceptor afterHandshake()");
        logger.info("exception: {} ", !ObjectUtils.isEmpty(exception) ? exception.getMessage() : "No Exceptions.");
    }
}
