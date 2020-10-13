package com.pocketchat.services.chat_message;

import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.models.controllers.request.chat_message.CreateChatMessageRequest;
import com.pocketchat.models.controllers.response.chat_message.ChatMessageResponse;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;

public interface ChatMessageService {
    ChatMessage addChatMessage(CreateChatMessageRequest chatMessage);

    MultimediaResponse uploadChatMessageMultimedia(String chatMessageId, MultipartFile multipartFile);

    File getChatMessageMultimedia(String chatMessageId) throws FileNotFoundException;

    void deleteChatMessage(String messageId);

    ChatMessage getSingleChatMessage(String messageId);

    Page<ChatMessage> getChatMessagesOfAConversation(String conversationGroupId, Pageable pageable);

    ChatMessage createChatMessageToChatMessage(CreateChatMessageRequest createChatMessageRequest);

    ChatMessageResponse chatMessageResponseMapper(ChatMessage chatMessage);

    Page<ChatMessageResponse> pageChatMessageResponseMapper(Page<ChatMessage> chatMessages);
}
