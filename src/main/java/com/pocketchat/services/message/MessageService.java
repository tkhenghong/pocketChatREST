package com.pocketchat.services.message;

import com.pocketchat.db.models.message.Message;

import java.util.List;

public interface MessageService {
    Message addMessage(Message message);

    void editMessage(Message message);

    void deleteMessage(String messageId);

    List<Message> getMessagesOfAConversation(String conversationGroupId);
}
