package com.pocketchat.server.configurations.websocket;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomizedWebSocketMessage {
    private String conversationGroup;
    private String message;
    private String multimedia;
    private String settings;
    private String unreadMessage;
    private String user;
    private String userContact;
}