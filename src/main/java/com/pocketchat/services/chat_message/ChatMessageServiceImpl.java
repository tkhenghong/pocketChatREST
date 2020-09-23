package com.pocketchat.services.chat_message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repo_services.chat_message.ChatMessageRepoService;
import com.pocketchat.models.controllers.request.chat_message.CreateChatMessageRequest;
import com.pocketchat.models.controllers.response.chat_message.ChatMessageResponse;
import com.pocketchat.models.enums.chat_message.ChatMessageStatus;
import com.pocketchat.models.websocket.WebSocketMessage;
import com.pocketchat.server.exceptions.chat_message.ChatMessageNotFoundException;
import com.pocketchat.server.exceptions.conversation_group.WebSocketObjectConversionFailedException;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import com.pocketchat.services.rabbitmq.RabbitMQService;
import com.pocketchat.services.user_contact.UserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepoService chatMessageRepoService;

    private final ConversationGroupService conversationGroupService;

    private final UserContactService userContactService;

    private final RabbitMQService rabbitMQService;

    private final ObjectMapper objectMapper;

    // @Lazy to prevent circular dependencies in Spring Boot. https://www.baeldung.com/circular-dependencies-in-spring
    @Autowired
    public ChatMessageServiceImpl(ChatMessageRepoService chatMessageRepoService,
                                  @Lazy ConversationGroupService conversationGroupService,
                                  UserContactService userContactService,
                                  RabbitMQService rabbitMQService,
                                  ObjectMapper objectMapper) {
        this.chatMessageRepoService = chatMessageRepoService;
        this.conversationGroupService = conversationGroupService;
        this.userContactService = userContactService;
        this.rabbitMQService = rabbitMQService;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public ChatMessage addChatMessage(CreateChatMessageRequest createChatMessageRequest) {
        ConversationGroup conversationGroup = conversationGroupService.getSingleConversation(createChatMessageRequest.getConversationId());

        ChatMessage chatMessage = createChatMessageToChatMessage(createChatMessageRequest);

        // *Attach sender details*
        UserContact senderUserContact = userContactService.getOwnUserContact();
        chatMessage.setSenderId(senderUserContact.getId());
        chatMessage.setSenderMobileNo(senderUserContact.getMobileNo());
        chatMessage.setSenderName(senderUserContact.getDisplayName());

        chatMessage.setChatMessageStatus(ChatMessageStatus.Sending); // Only this program determine the chat messages' status

        ChatMessage newChatMessage = chatMessageRepoService.save(chatMessage);

        sendMessageToRabbitMQ(conversationGroup, newChatMessage);

        return newChatMessage;
    }

    @Override
    @Transactional
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
        // rabbitMQService.listenMessagesFromQueue();
        return chatMessageList;
    }

    @Override
    public ChatMessage createChatMessageToChatMessage(CreateChatMessageRequest createChatMessageRequest) {
        return ChatMessage.builder()
                .chatMessageType(createChatMessageRequest.getChatMessageType())
                .conversationId(createChatMessageRequest.getConversationId())
                .messageContent(createChatMessageRequest.getMessageContent())
                .multimediaId(createChatMessageRequest.getMultimediaId())
                .createdTime(LocalDateTime.now())
                .build();
    }

    @Override
    public ChatMessageResponse chatMessageResponseMapper(ChatMessage chatMessage) {
        return ChatMessageResponse.builder()
                .id(chatMessage.getId())
                .chatMessageType(chatMessage.getChatMessageType())
                .chatMessageStatus(chatMessage.getChatMessageStatus())
                .conversationId(chatMessage.getConversationId())
                .messageContent(chatMessage.getMessageContent())
                .multimediaId(chatMessage.getMultimediaId())
                .senderId(chatMessage.getConversationId())
                .senderName(chatMessage.getSenderName())
                .senderMobileNo(chatMessage.getSenderMobileNo())
                .createdTime(chatMessage.getCreatedTime())
                .sentTime(chatMessage.getSentTime())
                .build();
    }

    private void sendMessageToRabbitMQ(ConversationGroup conversationGroup, ChatMessage chatMessage) {
        WebSocketMessage webSocketMessage = WebSocketMessage.builder()
                .chatMessage(chatMessage)
                .build();

        try {
            String webSocketMessageString = objectMapper.writeValueAsString(webSocketMessage);

            conversationGroup.getMemberIds().forEach(memberId ->
                    rabbitMQService.addMessageToQueue(memberId, conversationGroup.getId(),
                            conversationGroup.getId(), webSocketMessageString));
        } catch (JsonProcessingException e) {
            throw new WebSocketObjectConversionFailedException("Failed to convert Chat message to JSON string. Message: "
                    + e.getMessage());
        }
    }
}
