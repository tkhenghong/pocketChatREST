package com.pocketchat.services.message;

import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.models.controllers.request.message.CreateMessageRequest;
import com.pocketchat.models.controllers.request.message.UpdateMessageRequest;
import com.pocketchat.models.controllers.response.message.MessageResponse;

import java.util.List;

public interface MessageService {
    MessageResponse addMessage(CreateMessageRequest chatMessage);

    MessageResponse editMessage(UpdateMessageRequest chatMessage);

    void deleteMessage(String messageId);

    ChatMessage getSingleMessage(String messageId);

    List<MessageResponse> getMessagesOfAConversation(String conversationGroupId);

    ChatMessage createCreateChatMessageToChatMessage(CreateMessageRequest createMessageRequest);

    ChatMessage updateCreateChatMessageToChatMessage(UpdateMessageRequest updateMessageRequest);

    MessageResponse messageResponseMapper(ChatMessage chatMessage);
}
