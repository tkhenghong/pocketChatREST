package com.pocketchat.services.message;

import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.repo_services.conversationGroup.ConversationGroupRepoService;
import com.pocketchat.db.repo_services.message.MessageRepoService;
import com.pocketchat.models.controllers.request.message.CreateMessageRequest;
import com.pocketchat.models.controllers.request.message.UpdateMessageRequest;
import com.pocketchat.models.controllers.response.message.MessageResponse;
import com.pocketchat.server.exceptions.conversation_group.ConversationGroupNotFoundException;
import com.pocketchat.server.exceptions.message.MessageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepoService messageRepoService;

    private final ConversationGroupRepoService conversationGroupRepoService;

    @Autowired
    public MessageServiceImpl(MessageRepoService messageRepoService, ConversationGroupRepoService conversationGroupRepoService) {
        this.messageRepoService = messageRepoService;
        this.conversationGroupRepoService = conversationGroupRepoService;
    }

    @Override
    public MessageResponse addMessage(CreateMessageRequest createMessageRequest) {
        ChatMessage chatMessage = createCreateChatMessageToChatMessage(createMessageRequest);
        return messageResponseMapper(messageRepoService.save(chatMessage));
    }

    @Override
    public MessageResponse editMessage(UpdateMessageRequest updateMessageRequest) {
        ChatMessage chatMessage = updateCreateChatMessageToChatMessage(updateMessageRequest);
        getSingleMessage(chatMessage.getId());
        return messageResponseMapper(messageRepoService.save(chatMessage));
    }

    @Override
    public void deleteMessage(String messageId) {
        messageRepoService.delete(getSingleMessage(messageId));
    }

    @Override
    public ChatMessage getSingleMessage(String messageId) {
        Optional<ChatMessage> messageOptional = messageRepoService.findById(messageId);
        if (!messageOptional.isPresent()) {
            throw new MessageNotFoundException("messageId-" + messageId);
        }
        return messageOptional.get();
    }

    @Override
    public List<MessageResponse> getMessagesOfAConversation(String conversationGroupId) {
        Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.findById(conversationGroupId);
        if (!conversationGroupOptional.isPresent()) {
            throw new ConversationGroupNotFoundException("conversationGroupId:-" + conversationGroupId);
        }
        List<ChatMessage> chatMessageList = messageRepoService.findAllMessagesByConversationId(conversationGroupId);
        if (chatMessageList.isEmpty()) {
            throw new MessageNotFoundException("No message found for this conversationGroupId:-" + conversationGroupId);
        }
        return chatMessageList.stream().map(this::messageResponseMapper).collect(Collectors.toList());
    }

    @Override
    public ChatMessage createCreateChatMessageToChatMessage(CreateMessageRequest createMessageRequest) {
        return ChatMessage.builder()
                .id(createMessageRequest.getId())
                .conversationId(createMessageRequest.getConversationId())
                .createdTime(createMessageRequest.getCreatedTime())
                .messageContent(createMessageRequest.getMessageContent())
                .multimediaId(createMessageRequest.getMultimediaId())
                .receiverId(createMessageRequest.getReceiverId())
                .receiverMobileNo(createMessageRequest.getReceiverMobileNo())
                .receiverName(createMessageRequest.getReceiverName())
                .senderId(createMessageRequest.getConversationId())
                .senderMobileNo(createMessageRequest.getSenderMobileNo())
                .senderName(createMessageRequest.getSenderName())
                .sentTime(createMessageRequest.getSentTime())
                .status(createMessageRequest.getStatus())
                .type(createMessageRequest.getType())
                .build();
    }

    @Override
    public ChatMessage updateCreateChatMessageToChatMessage(UpdateMessageRequest updateMessageRequest) {
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
    public MessageResponse messageResponseMapper(ChatMessage chatMessage) {
        return MessageResponse.builder()
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
