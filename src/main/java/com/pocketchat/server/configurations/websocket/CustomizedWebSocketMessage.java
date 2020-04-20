package com.pocketchat.server.configurations.websocket;

public class CustomizedWebSocketMessage {
    private String conversationGroup;
    private String message;
    private String multimedia;
    private String settings;
    private String unreadMessage;
    private String user;
    private String userContact;

    public String getConversationGroup() {
        return this.conversationGroup;
    }

    public String getMessage() {
        return this.message;
    }

    public String getMultimedia() {
        return this.multimedia;
    }

    public String getSettings() {
        return this.settings;
    }

    public String getUnreadMessage() {
        return this.unreadMessage;
    }

    public String getUser() {
        return this.user;
    }

    public String getUserContact() {
        return this.userContact;
    }

    public void setConversationGroup(String conversationGroup) {
        this.conversationGroup = conversationGroup;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMultimedia(String multimedia) {
        this.multimedia = multimedia;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public void setUnreadMessage(String unreadMessage) {
        this.unreadMessage = unreadMessage;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String toString() {
        return "CustomizedWebSocketMessage(conversationGroup=" + this.getConversationGroup() + ", message=" + this.getMessage() + ", multimedia=" + this.getMultimedia() + ", settings=" + this.getSettings() + ", unreadMessage=" + this.getUnreadMessage() + ", user=" + this.getUser() + ", userContact=" + this.getUserContact() + ")";
    }
}