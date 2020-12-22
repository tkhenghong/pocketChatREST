package com.pocketchat.services.chat_message.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repo_services.chat_message.ChatMessageRepoService;
import com.pocketchat.models.controllers.request.chat_message.CreateChatMessageRequest;
import com.pocketchat.models.enums.chat_message.ChatMessageStatus;
import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import com.pocketchat.server.configurations.websocket.WebSocketMessageSender;
import com.pocketchat.server.exceptions.conversation_group.ConversationGroupNotFoundException;
import com.pocketchat.server.exceptions.user_contact.UserContactNotFoundException;
import com.pocketchat.services.chat_message.ChatMessageService;
import com.pocketchat.services.chat_message.ChatMessageServiceImpl;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import com.pocketchat.services.multimedia.MultimediaService;
import com.pocketchat.services.rabbitmq.RabbitMQService;
import com.pocketchat.services.user_contact.UserContactService;
import com.pocketchat.utils.file.FileUtil;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ChatMessageServiceTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ChatMessageService chatMessageService;

    @Mock
    private ChatMessageRepoService chatMessageRepoService;

    @Mock
    private ConversationGroupService conversationGroupService;

    @Mock
    private UserContactService userContactService;

    @Mock
    private MultimediaService multimediaService;

    @Mock
    private RabbitMQService rabbitMQService;

    @Mock
    WebSocketMessageSender webSocketMessageSender;

    @Mock
    private FileUtil fileUtil;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        chatMessageService = new ChatMessageServiceImpl(
                chatMessageRepoService,
                conversationGroupService,
                userContactService,
                multimediaService,
                rabbitMQService,
                webSocketMessageSender,
                fileUtil,
                objectMapper
        );
    }

    /**
     * Test addChatMessage() but Conversation Group ID either not exist or not found.
     */
    @Test
    void testAddChatMessageButConversationGroupDoesNotFound() {
        CreateChatMessageRequest createChatMessageRequest = generateChatMessageRequestObject();

        try {
            Mockito.when(conversationGroupService.getSingleConversation(eq(createChatMessageRequest.getConversationId())))
                    .thenThrow(ConversationGroupNotFoundException.class);

            chatMessageService.addChatMessage(createChatMessageRequest);
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception exception) {
            Mockito.verify(conversationGroupService).getSingleConversation(eq(createChatMessageRequest.getConversationId()));

            assertThat(exception).isInstanceOf(ConversationGroupNotFoundException.class);
        }
    }

    /**
     * Test addChatMessage() but unable to get own user contact.
     */
    @Test
    void testAddChatMessageButUnableToGetOwnUserContact() {
        CreateChatMessageRequest createChatMessageRequest = generateChatMessageRequestObject();
        ConversationGroup conversationGroup = generateConversationGroupObject();

        try {
            Mockito.when(conversationGroupService.getSingleConversation(eq(createChatMessageRequest.getConversationId())))
                    .thenReturn(conversationGroup);
            Mockito.when(userContactService.getOwnUserContact()).thenThrow(UserContactNotFoundException.class);

            chatMessageService.addChatMessage(createChatMessageRequest);
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception exception) {
            Mockito.verify(conversationGroupService).getSingleConversation(eq(createChatMessageRequest.getConversationId()));
            Mockito.verify(userContactService).getOwnUserContact();

            assertThat(exception).isInstanceOf(UserContactNotFoundException.class);
        }
    }

    /**
     * Test addChatMessage().
     */
    @Test
    void addChatMessage() throws JsonProcessingException {
        CreateChatMessageRequest createChatMessageRequest = generateChatMessageRequestObject();
        ConversationGroup conversationGroup = generateConversationGroupObject();
        String webSocketMessageString = UUID.randomUUID().toString();
        UserContact userContact = generateUserContactObject();

        Mockito.when(conversationGroupService.getSingleConversation(eq(createChatMessageRequest.getConversationId())))
                .thenReturn(conversationGroup);
        Mockito.when(userContactService.getOwnUserContact()).thenReturn(userContact);
        Mockito.when(chatMessageRepoService.save(any())).thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(objectMapper.writeValueAsString(any())).thenReturn(webSocketMessageString);

        ChatMessage chatMessage = chatMessageService.addChatMessage(createChatMessageRequest);

        Mockito.verify(conversationGroupService).getSingleConversation(eq(createChatMessageRequest.getConversationId()));
        Mockito.verify(userContactService).getOwnUserContact();
        Mockito.verify(objectMapper).writeValueAsString(any());

        Mockito.verify(rabbitMQService, times(conversationGroup.getMemberIds().size())).addMessageToQueue(anyString(), eq(conversationGroup.getId()), eq(conversationGroup.getId()),
                eq(webSocketMessageString));

        assertNotNull(chatMessage);
        assertEquals(userContact.getId(), chatMessage.getSenderId());
        assertEquals(userContact.getMobileNo(), chatMessage.getSenderMobileNo());
        assertEquals(userContact.getDisplayName(), chatMessage.getSenderName());
        assertEquals(ChatMessageStatus.Sending, chatMessage.getChatMessageStatus());
    }

    // deleteChatMessage
    // getSingleChatMessage
    // getChatMessagesOfAConversation

    private CreateChatMessageRequest generateChatMessageRequestObject() {
        return CreateChatMessageRequest.builder()
                .conversationId(UUID.randomUUID().toString())
                .messageContent(UUID.randomUUID().toString())
                .build();
    }

    private ConversationGroup generateConversationGroupObject() {
        List<String> memberIds = generateRandomObjectIds(10);

        return ConversationGroup.builder()
                .id(UUID.randomUUID().toString())
                .conversationGroupType(ConversationGroupType.Group)
                .name(UUID.randomUUID().toString())
                .memberIds(memberIds)
                .adminMemberIds(Collections.singletonList(memberIds.get(0)))
                .description(UUID.randomUUID().toString())
                .creatorUserId(memberIds.get(0))
                .build();
    }

    private UserContact generateUserContactObject() {
        return UserContact.builder()
                .id(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .userIds(new ArrayList<>())
                .displayName(UUID.randomUUID().toString())
                .realName(UUID.randomUUID().toString())
                .countryCode(UUID.randomUUID().toString())
                .profilePicture(UUID.randomUUID().toString())
                .mobileNo(UUID.randomUUID().toString())
                .about(UUID.randomUUID().toString())
                .build();
    }

    private List<String> generateRandomObjectIds(Integer numberOfObjectIds) {
        List<String> objectIds = new ArrayList<>();
        for (int i = 0; i < numberOfObjectIds; i++) {
            objectIds.add(ObjectId.get().toHexString());
        }

        return objectIds;
    }
}
