package com.pocketchat.services.chat_message;

import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.models.controllers.request.chat_message.CreateChatChatMessageRequest;
import com.pocketchat.models.controllers.request.chat_message.UpdateChatMessageRequest;
import com.pocketchat.models.controllers.response.chat_message.ChatMessageResponse;

import java.util.List;

public interface ChatMessageService {
    ChatMessageResponse addChatMessage(CreateChatChatMessageRequest chatMessage);

    ChatMessageResponse editChatMessage(UpdateChatMessageRequest chatMessage);

    void deleteChatMessage(String messageId);

    ChatMessage getSingleChatMessage(String messageId);

    List<ChatMessageResponse> getChatMessagesOfAConversation(String conversationGroupId);

    ChatMessage createCreateChatMessageToChatMessage(CreateChatChatMessageRequest createChatMessageRequest);

    ChatMessage updateCreateChatMessageToChatMessage(UpdateChatMessageRequest updateMessageRequest);

    ChatMessageResponse chatMessageResponseMapper(ChatMessage chatMessage);
}
