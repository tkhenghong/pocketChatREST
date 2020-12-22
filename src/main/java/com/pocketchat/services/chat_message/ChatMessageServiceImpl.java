package com.pocketchat.services.chat_message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repo_services.chat_message.ChatMessageRepoService;
import com.pocketchat.models.controllers.request.chat_message.CreateChatMessageRequest;
import com.pocketchat.models.controllers.response.chat_message.ChatMessageResponse;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.models.enums.chat_message.ChatMessageStatus;
import com.pocketchat.models.enums.websocket.WebSocketEvent;
import com.pocketchat.models.websocket.WebSocketMessage;
import com.pocketchat.server.configurations.websocket.WebSocketMessageSender;
import com.pocketchat.server.exceptions.chat_message.ChatMessageNotFoundException;
import com.pocketchat.server.exceptions.conversation_group.WebSocketObjectConversionFailedException;
import com.pocketchat.server.exceptions.file.UploadFileException;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import com.pocketchat.services.multimedia.MultimediaService;
import com.pocketchat.services.rabbitmq.RabbitMQService;
import com.pocketchat.services.user_contact.UserContactService;
import com.pocketchat.utils.file.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ChatMessageRepoService chatMessageRepoService;

    private final ConversationGroupService conversationGroupService;

    private final UserContactService userContactService;

    private final MultimediaService multimediaService;

    private final RabbitMQService rabbitMQService;

    private final WebSocketMessageSender webSocketMessageSender;

    private final FileUtil fileUtil;

    private final ObjectMapper objectMapper;

    private final String moduleDirectory = "chatMessage";

    // @Lazy to prevent circular dependencies in Spring Boot. https://www.baeldung.com/circular-dependencies-in-spring
    @Autowired
    public ChatMessageServiceImpl(ChatMessageRepoService chatMessageRepoService,
                                  @Lazy ConversationGroupService conversationGroupService,
                                  UserContactService userContactService,
                                  MultimediaService multimediaService,
                                  RabbitMQService rabbitMQService,
                                  WebSocketMessageSender webSocketMessageSender,
                                  FileUtil fileUtil,
                                  ObjectMapper objectMapper) {
        this.chatMessageRepoService = chatMessageRepoService;
        this.conversationGroupService = conversationGroupService;
        this.userContactService = userContactService;
        this.multimediaService = multimediaService;
        this.rabbitMQService = rabbitMQService;
        this.webSocketMessageSender = webSocketMessageSender;
        this.fileUtil = fileUtil;
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

        String webSocketMessageString = convertChatMessageToWebSocketMessageString(newChatMessage, WebSocketEvent.ADDED_CHAT_MESSAGE);

        sendMessageToRabbitMQ(conversationGroup, webSocketMessageString);

        // Send to WebSocket users.(Current connected frontend clients)
        webSocketMessageSender.sendMessageToWebSocketUsers(webSocketMessageString, conversationGroup.getMemberIds());

        // TODO: Send notifications for current not connected frontend clients. (Phone & Web)

        return newChatMessage;
    }

    @Override
    public MultimediaResponse uploadChatMessageMultimedia(String chatMessageId, MultipartFile multipartFile) {
        ChatMessage chatMessage = getSingleChatMessage(chatMessageId);

        ConversationGroup conversationGroup = conversationGroupService.getSingleConversation(chatMessage.getConversationId());

        Multimedia savedMultimedia;
        try {
            savedMultimedia = multimediaService.addMultimedia(fileUtil.createMultimedia(multipartFile, moduleDirectory));
        } catch (IOException ioException) {
            throw new UploadFileException("Unable to upload chat message multimedia to the server! chatMessageId: " + chatMessageId + ", message: " + ioException.getMessage());
        }

        if (ObjectUtils.isEmpty(savedMultimedia)) {
            throw new UploadFileException("Unable to upload chat message multimedia to the server due to savedMultimedia object is empty! chatMessageId: " + chatMessageId);
        }

        String webSocketMessageString = convertChatMessageToWebSocketMessageString(chatMessage, WebSocketEvent.UPLOADED_CHAT_MESSAGE_MULTIMEDIA);

        sendMessageToRabbitMQ(conversationGroup, webSocketMessageString);

        // Send to WebSocket users.(Current connected frontend clients)
        webSocketMessageSender.sendMessageToWebSocketUsers(webSocketMessageString, conversationGroup.getMemberIds());

        // TODO: Send notifications for current not connected frontend clients. (Phone & Web)

        return multimediaService.multimediaResponseMapper(savedMultimedia);
    }

    @Override
    public File getChatMessageMultimedia(String chatMessageId) throws FileNotFoundException {
        Multimedia multimedia = multimediaService.getSingleMultimedia(getSingleChatMessage(chatMessageId).getMultimediaId());
        return fileUtil.getFileWithAbsolutePath(moduleDirectory, multimedia.getFileDirectory(), multimedia.getFileName());
    }

    @Override
    @Transactional
    public void deleteChatMessage(String messageId) {
        ChatMessage chatMessage = getSingleChatMessage(messageId);
        ConversationGroup conversationGroup = conversationGroupService.getSingleConversation(chatMessage.getConversationId());

        if (StringUtils.hasText(chatMessage.getMultimediaId())) {
            multimediaService.deleteMultimedia(chatMessage.getMultimediaId(), moduleDirectory);
        }

        // TODO: Send Websocket message and notification to replace the message to "Message removed" to the correct conversation group members.

        String webSocketMessageString = convertChatMessageToWebSocketMessageString(chatMessage, WebSocketEvent.DELETED_CHAT_MESSAGE);

        sendMessageToRabbitMQ(conversationGroup, webSocketMessageString);

        // Send to WebSocket users.(Current connected frontend clients)
        webSocketMessageSender.sendMessageToWebSocketUsers(webSocketMessageString, conversationGroup.getMemberIds());

        chatMessageRepoService.delete(chatMessage);
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
    public Page<ChatMessage> getChatMessagesOfAConversation(String conversationGroupId, Pageable pageable) {
        conversationGroupService.getSingleConversation(conversationGroupId);
        return chatMessageRepoService.findAllMessagesByConversationId(conversationGroupId, pageable);
    }

    @Override
    public ChatMessage createChatMessageToChatMessage(CreateChatMessageRequest createChatMessageRequest) {
        return ChatMessage.builder()
                .conversationId(createChatMessageRequest.getConversationId())
                .messageContent(createChatMessageRequest.getMessageContent())
                .build();
    }

    @Override
    public ChatMessageResponse chatMessageResponseMapper(ChatMessage chatMessage) {
        return ChatMessageResponse.builder()
                .id(chatMessage.getId())
                .chatMessageStatus(chatMessage.getChatMessageStatus())
                .conversationId(chatMessage.getConversationId())
                .messageContent(chatMessage.getMessageContent())
                .multimediaId(chatMessage.getMultimediaId())
                .senderId(chatMessage.getConversationId())
                .senderName(chatMessage.getSenderName())
                .senderMobileNo(chatMessage.getSenderMobileNo())
                .createdBy(chatMessage.getCreatedBy())
                .createdDate(chatMessage.getCreatedDate())
                .lastModifiedBy(chatMessage.getLastModifiedBy())
                .lastModifiedDate(chatMessage.getLastModifiedDate())
                .version(chatMessage.getVersion())
                .build();
    }

    // How to map Page<ObjectEntity> to Page<ObjectDTO> in spring-data-rest:
    // https://stackoverflow.com/questions/39036771
    // Then, optimized it with lambda reference Java 8
    @Override
    public Page<ChatMessageResponse> pageChatMessageResponseMapper(Page<ChatMessage> chatMessages) {
        return chatMessages.map(this::chatMessageResponseMapper);
    }

    private void sendMessageToRabbitMQ(ConversationGroup conversationGroup, String webSocketMessageString) {
        conversationGroup.getMemberIds().forEach(memberId ->
                rabbitMQService.addMessageToQueue(memberId, conversationGroup.getId(),
                        conversationGroup.getId(), webSocketMessageString));
    }

    private String convertChatMessageToWebSocketMessageString(ChatMessage chatMessage, WebSocketEvent webSocketEvent) {
        WebSocketMessage webSocketMessage = WebSocketMessage.builder()
                .chatMessage(chatMessage)
                .webSocketEvent(webSocketEvent)
                .build();
        return convertWebSocketMessageToString(webSocketMessage);
    }

    private String convertWebSocketMessageToString(WebSocketMessage webSocketMessage) {
        try {
            return objectMapper.writeValueAsString(webSocketMessage);
        } catch (JsonProcessingException e) {
            throw new WebSocketObjectConversionFailedException("Failed to convert Chat message to JSON string. Message: "
                    + e.getMessage());
        }
    }
}
