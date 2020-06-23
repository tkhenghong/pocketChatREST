package com.pocketchat.services.chat_message;

import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.models.controllers.request.chat_message.CreateChatMessageRequest;
import com.pocketchat.models.controllers.request.chat_message.UpdateChatMessageRequest;
import com.pocketchat.models.controllers.response.chat_message.ChatMessageResponse;

import java.util.List;

public interface ChatMessageService {
    ChatMessage addChatMessage(CreateChatMessageRequest chatMessage);

    ChatMessage editChatMessage(UpdateChatMessageRequest chatMessage);

    void deleteChatMessage(String messageId);

    ChatMessage getSingleChatMessage(String messageId);

    List<ChatMessage> getChatMessagesOfAConversation(String conversationGroupId);

    ChatMessage createChatMessageToChatMessage(CreateChatMessageRequest createChatMessageRequest);

    ChatMessage updateChatMessageToChatMessage(UpdateChatMessageRequest updateMessageRequest);

    ChatMessageResponse chatMessageResponseMapper(ChatMessage chatMessage);
}
