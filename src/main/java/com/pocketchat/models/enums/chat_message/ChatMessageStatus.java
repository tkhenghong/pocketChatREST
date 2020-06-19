package com.pocketchat.models.enums.chat_message;

public enum ChatMessageStatus {
    Sending("Sending"),
    Sent("Sent"),
    Received("Received"),
    Read("Read")
    ;

    private final String chatMessageStatus;

    ChatMessageStatus(String chatMessageStatus) {
        this.chatMessageStatus = chatMessageStatus;
    }

    public String getChatMessageStatus() {
        return chatMessageStatus;
    }
}
