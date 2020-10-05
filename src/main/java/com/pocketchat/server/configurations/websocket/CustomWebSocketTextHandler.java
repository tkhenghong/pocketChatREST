package com.pocketchat.server.configurations.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class CustomWebSocketTextHandler extends TextWebSocketHandler {
}
