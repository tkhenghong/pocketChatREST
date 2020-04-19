package com.pocketchat.services.models.message;

import com.pocketchat.db.models.chat_message.ChatMessage;

import java.util.List;

public interface MessageService {
    ChatMessage addMessage(ChatMessage chatMessage);

    void editMessage(ChatMessage chatMessage);

    void deleteMessage(String messageId);

    ChatMessage getSingleMessage(String messageId);

    List<ChatMessage> getMessagesOfAConversation(String conversationGroupId);
}
