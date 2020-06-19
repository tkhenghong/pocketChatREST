package com.pocketchat.models.enums.chat_message;

public enum ChatMessageType {
    Text("Text"),
    Image("Image"),
    Video("Video"),
    Audio("Audio"),
    File("File"),
    Document("Document"),
    GIF("GIF");

    private final String chatMessageType;

    ChatMessageType(String chatMessageType) {
        this.chatMessageType = chatMessageType;
    }

    public String getChatMessageType() {
        return chatMessageType;
    }
}
