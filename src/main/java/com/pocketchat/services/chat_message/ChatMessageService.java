package com.pocketchat.services.chat_message;

import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.models.controllers.request.chat_message.CreateChatMessageRequest;
import com.pocketchat.models.controllers.request.chat_message.UpdateChatMessageRequest;
import com.pocketchat.models.controllers.response.chat_message.ChatMessageResponse;

import java.util.List;

public interface ChatMessageService {
    ChatMessageResponse addChatMessage(CreateChatMessageRequest chatMessage);

    ChatMessageResponse editChatMessage(UpdateChatMessageRequest chatMessage);

    void deleteChatMessage(String messageId);

    ChatMessage getSingleChatMessage(String messageId);

    List<ChatMessageResponse> getChatMessagesOfAConversation(String conversationGroupId);

    ChatMessage createCreateChatMessageToChatMessage(CreateChatMessageRequest createChatMessageRequest);

    ChatMessage updateCreateChatMessageToChatMessage(UpdateChatMessageRequest updateMessageRequest);

    ChatMessageResponse chatMessageResponseMapper(ChatMessage chatMessage);
}
