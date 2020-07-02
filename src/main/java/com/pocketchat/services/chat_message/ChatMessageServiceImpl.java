package com.pocketchat.services.chat_message;

import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.repo_services.chat_message.ChatMessageRepoService;
import com.pocketchat.models.controllers.request.chat_message.CreateChatMessageRequest;
import com.pocketchat.models.controllers.request.chat_message.UpdateChatMessageRequest;
import com.pocketchat.models.controllers.response.chat_message.ChatMessageResponse;
import com.pocketchat.models.enums.chat_message.ChatMessageStatus;
import com.pocketchat.models.enums.chat_message.ChatMessageType;
import com.pocketchat.models.websocket.WebSocketMessage;
import com.pocketchat.server.exceptions.chat_message.ChatMessageNotFoundException;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import com.pocketchat.services.rabbitmq.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepoService chatMessageRepoService;

    private final ConversationGroupService conversationGroupService;

    private final RabbitMQService rabbitMQService;

    @Autowired
    public ChatMessageServiceImpl(ChatMessageRepoService chatMessageRepoService,
                                  ConversationGroupService conversationGroupService,
                                  RabbitMQService rabbitMQService) {
        this.chatMessageRepoService = chatMessageRepoService;
        this.conversationGroupService = conversationGroupService;
        this.rabbitMQService = rabbitMQService;
    }

    @Override
    public ChatMessage addChatMessage(CreateChatMessageRequest createChatMessageRequest) {
        ConversationGroup conversationGroup = conversationGroupService.getSingleConversation(createChatMessageRequest.getConversationId());

        ChatMessage chatMessage = createChatMessageToChatMessage(createChatMessageRequest);

        ChatMessage newChatMessage = chatMessageRepoService.save(chatMessage);

        sendMessageToRabbitMQ(conversationGroup, newChatMessage);

        return newChatMessage;
    }

    @Override
    public ChatMessage editChatMessage(UpdateChatMessageRequest updateMessageRequest) {
        conversationGroupService.getSingleConversation(updateMessageRequest.getConversationId());

        getSingleChatMessage(updateMessageRequest.getId());

        ChatMessage chatMessage = updateChatMessageToChatMessage(updateMessageRequest);

        return chatMessageRepoService.save(chatMessage);
    }

    @Override
    public void deleteChatMessage(String messageId) {
        chatMessageRepoService.delete(getSingleChatMessage(messageId));
    }

    @Override
    public ChatMessage getSingleChatMessage(String messageId) {
        Optional<ChatMessage> messageOptional = chatMessageRepoService.findById(messageId);
        if (messageOptional.isEmpty()) {
            throw new ChatMessageNotFoundException("messageId-" + messageId);
        }
        return messageOptional.get();
    }

    @Override
    public List<ChatMessage> getChatMessagesOfAConversation(String conversationGroupId) {
        conversationGroupService.getSingleConversation(conversationGroupId);
        List<ChatMessage> chatMessageList = chatMessageRepoService.findAllMessagesByConversationId(conversationGroupId);
        if (chatMessageList.isEmpty()) {
            throw new ChatMessageNotFoundException("No message found for this conversationGroupId:-" + conversationGroupId);
        }
        return chatMessageList;
    }

    @Override
    public ChatMessage createChatMessageToChatMessage(CreateChatMessageRequest createChatMessageRequest) {
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
                .status(ChatMessageStatus.valueOf(createChatMessageRequest.getStatus()))
                .type(ChatMessageType.valueOf(createChatMessageRequest.getType()))
                .build();
    }

    @Override
    public ChatMessage updateChatMessageToChatMessage(UpdateChatMessageRequest updateMessageRequest) {
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
                .status(ChatMessageStatus.valueOf(updateMessageRequest.getStatus()))
                .type(ChatMessageType.valueOf(updateMessageRequest.getType()))
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
                .status(chatMessage.getStatus().getChatMessageStatus())
                .type(chatMessage.getType().getChatMessageType())
                .build();
    }

    private void sendMessageToRabbitMQ(ConversationGroup conversationGroup, ChatMessage chatMessage) {
        WebSocketMessage webSocketMessage = WebSocketMessage.builder()
                .chatMessage(chatMessage)
                .build();

        conversationGroup.getMemberIds().forEach(memberId -> {
            rabbitMQService.addMessageToQueue(memberId, conversationGroup.getId(), conversationGroup.getId(), webSocketMessage.toString());
        });
    }
}
