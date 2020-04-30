package com.pocketchat.services.chat_message;

import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.repo_services.conversation_group.ConversationGroupRepoService;
import com.pocketchat.db.repo_services.chat_message.ChatMessageRepoService;
import com.pocketchat.models.controllers.request.chat_message.CreateChatChatMessageRequest;
import com.pocketchat.models.controllers.request.chat_message.UpdateChatMessageRequest;
import com.pocketchat.models.controllers.response.chat_message.ChatMessageResponse;
import com.pocketchat.server.exceptions.conversation_group.ConversationGroupNotFoundException;
import com.pocketchat.server.exceptions.chat_message.ChatMessageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepoService chatMessageRepoService;

    private final ConversationGroupRepoService conversationGroupRepoService;

    @Autowired
    public ChatMessageServiceImpl(ChatMessageRepoService chatMessageRepoService, ConversationGroupRepoService conversationGroupRepoService) {
        this.chatMessageRepoService = chatMessageRepoService;
        this.conversationGroupRepoService = conversationGroupRepoService;
    }

    @Override
    public ChatMessageResponse addChatMessage(CreateChatChatMessageRequest createChatMessageRequest) {
        ChatMessage chatMessage = createCreateChatMessageToChatMessage(createChatMessageRequest);
        return chatMessageResponseMapper(chatMessageRepoService.save(chatMessage));
    }

    @Override
    public ChatMessageResponse editChatMessage(UpdateChatMessageRequest updateMessageRequest) {
        ChatMessage chatMessage = updateCreateChatMessageToChatMessage(updateMessageRequest);
        getSingleChatMessage(chatMessage.getId());
        return chatMessageResponseMapper(chatMessageRepoService.save(chatMessage));
    }

    @Override
    public void deleteChatMessage(String messageId) {
        chatMessageRepoService.delete(getSingleChatMessage(messageId));
    }

    @Override
    public ChatMessage getSingleChatMessage(String messageId) {
        Optional<ChatMessage> messageOptional = chatMessageRepoService.findById(messageId);
        if (!messageOptional.isPresent()) {
            throw new ChatMessageNotFoundException("messageId-" + messageId);
        }
        return messageOptional.get();
    }

    @Override
    public List<ChatMessageResponse> getChatMessagesOfAConversation(String conversationGroupId) {
        Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.findById(conversationGroupId);
        if (!conversationGroupOptional.isPresent()) {
            throw new ConversationGroupNotFoundException("conversationGroupId:-" + conversationGroupId);
        }
        List<ChatMessage> chatMessageList = chatMessageRepoService.findAllMessagesByConversationId(conversationGroupId);
        if (chatMessageList.isEmpty()) {
            throw new ChatMessageNotFoundException("No message found for this conversationGroupId:-" + conversationGroupId);
        }
        return chatMessageList.stream().map(this::chatMessageResponseMapper).collect(Collectors.toList());
    }

    @Override
    public ChatMessage createCreateChatMessageToChatMessage(CreateChatChatMessageRequest createChatMessageRequest) {
        return ChatMessage.builder()
                .id(createChatMessageRequest.getId())
                .conversationId(createChatMessageRequest.getConversationId())
                .createdTime(createChatMessageRequest.getCreatedTime())
                .messageContent(createChatMessageRequest.getMessageContent())
                .multimediaId(createChatMessageRequest.getMultimediaId())
                .receiverId(createChatMessageRequest.getReceiverId())
                .receiverMobileNo(createChatMessageRequest.getReceiverMobileNo())
                .receiverName(createChatMessageRequest.getReceiverName())
                .senderId(createChatMessageRequest.getConversationId())
                .senderMobileNo(createChatMessageRequest.getSenderMobileNo())
                .senderName(createChatMessageRequest.getSenderName())
                .sentTime(createChatMessageRequest.getSentTime())
                .status(createChatMessageRequest.getStatus())
                .type(createChatMessageRequest.getType())
                .build();
    }

    @Override
    public ChatMessage updateCreateChatMessageToChatMessage(UpdateChatMessageRequest updateMessageRequest) {
        return ChatMessage.builder()
                .id(updateMessageRequest.getId())
                .conversationId(updateMessageRequest.getConversationId())
                .createdTime(updateMessageRequest.getCreatedTime())
                .messageContent(updateMessageRequest.getMessageContent())
                .multimediaId(updateMessageRequest.getMultimediaId())
                .receiverId(updateMessageRequest.getReceiverId())
                .receiverMobileNo(updateMessageRequest.getReceiverMobileNo())
                .receiverName(updateMessageRequest.getReceiverName())
                .senderId(updateMessageRequest.getConversationId())
                .senderMobileNo(updateMessageRequest.getSenderMobileNo())
                .senderName(updateMessageRequest.getSenderName())
                .sentTime(updateMessageRequest.getSentTime())
                .status(updateMessageRequest.getStatus())
                .type(updateMessageRequest.getType())
                .build();
    }

    @Override
    public ChatMessageResponse chatMessageResponseMapper(ChatMessage chatMessage) {
        return ChatMessageResponse.builder()
                .id(chatMessage.getId())
                .conversationId(chatMessage.getConversationId())
                .createdTime(chatMessage.getCreatedTime())
                .messageContent(chatMessage.getMessageContent())
                .multimediaId(chatMessage.getMultimediaId())
                .receiverId(chatMessage.getReceiverId())
                .receiverMobileNo(chatMessage.getReceiverMobileNo())
                .receiverName(chatMessage.getReceiverName())
                .senderId(chatMessage.getConversationId())
                .senderMobileNo(chatMessage.getSenderMobileNo())
                .senderName(chatMessage.getSenderName())
                .sentTime(chatMessage.getSentTime())
                .status(chatMessage.getStatus())
                .type(chatMessage.getType())
                .build();
    }
}
