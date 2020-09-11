package com.pocketchat.services.unread_message.service;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.unread_message.UnreadMessage;
import com.pocketchat.db.repo_services.unread_message.UnreadMessageRepoService;
import com.pocketchat.models.controllers.request.unread_message.CreateUnreadMessageRequest;
import com.pocketchat.models.controllers.request.unread_message.UpdateUnreadMessageRequest;
import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import com.pocketchat.server.exceptions.conversation_group.ConversationGroupNotFoundException;
import com.pocketchat.server.exceptions.unread_message.UnreadMessageNotFoundException;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import com.pocketchat.services.unread_message.UnreadMessageService;
import com.pocketchat.services.unread_message.UnreadMessageServiceImpl;
import com.pocketchat.services.user.UserService;
import com.pocketchat.services.user_authentication.UserAuthenticationService;
import com.pocketchat.services.user_contact.UserContactService;
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
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UnreadMessageServiceTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UnreadMessageService unreadMessageService;

    @Mock
    private UnreadMessageRepoService unreadMessageRepoService;

    @Mock
    private UserAuthenticationService userAuthenticationService;

    @Mock
    private UserContactService userContactService;

    @Mock
    private ConversationGroupService conversationGroupService;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        unreadMessageService = new UnreadMessageServiceImpl(
                unreadMessageRepoService,
                userAuthenticationService,
                userContactService,
                conversationGroupService,
                userService
        );
    }

    /**
     * Test UnreadMessageService.addUnreadMessage() with invalid conversation group ID.
     */
    @Test
    void testAddUnreadMessageWithInvalidConversationGroupId() {
        CreateUnreadMessageRequest createUnreadMessageRequest = generateCreateUnreadMessageRequestObject();

        Mockito.when(conversationGroupService.getSingleConversation(eq(createUnreadMessageRequest.getConversationId())))
                .thenThrow(new ConversationGroupNotFoundException(""));

        try {
            unreadMessageService.addUnreadMessage(createUnreadMessageRequest);
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception exception) {
            Mockito.verify(conversationGroupService).getSingleConversation(eq(createUnreadMessageRequest.getConversationId()));

            assertThat(exception).isInstanceOf(ConversationGroupNotFoundException.class);
        }
    }

    /**
     * Test UnreadMessageService.addUnreadMessage() but there's an UnreadMessage object with same conversation group ID.
     */
    @Test
    void testAddUnreadMessageButUnreadMessageExistingWithSameConversationGroupID() {
        CreateUnreadMessageRequest createUnreadMessageRequest = generateCreateUnreadMessageRequestObject();
        ConversationGroup conversationGroup = generateConversationGroupObject();
        UnreadMessage unreadMessage = generateUnreadMessageObject();

        Mockito.when(conversationGroupService.getSingleConversation(eq(createUnreadMessageRequest.getConversationId())))
                .thenReturn(conversationGroup);
        Mockito.when(unreadMessageRepoService.findByConversationGroupId(eq(createUnreadMessageRequest.getConversationId())))
                .thenReturn(Optional.of(unreadMessage));

        UnreadMessage savedUnreadMessage = unreadMessageService.addUnreadMessage(createUnreadMessageRequest);

        Mockito.verify(conversationGroupService).getSingleConversation(eq(createUnreadMessageRequest.getConversationId()));
        Mockito.verify(unreadMessageRepoService).findByConversationGroupId(eq(createUnreadMessageRequest.getConversationId()));
        Mockito.verify(unreadMessageRepoService, times(0)).save(any());

        assertNotNull(savedUnreadMessage);
        assertEquals(savedUnreadMessage.getConversationId(), unreadMessage.getConversationId());
        // Make sure the logic doesn't save from createUnreadMessageRequest's conversationGroupId.
        assertNotEquals(savedUnreadMessage.getConversationId(), createUnreadMessageRequest.getConversationId());
        assertNotEquals(savedUnreadMessage.getConversationId(), conversationGroup.getId());
    }

    /**
     * Test UnreadMessageService.addUnreadMessage(). No existing UnreadMessage in DB.
     */
    @Test
    void testAddUnreadMessage() {
        CreateUnreadMessageRequest createUnreadMessageRequest = generateCreateUnreadMessageRequestObject();
        ConversationGroup conversationGroup = generateConversationGroupObject();
        UnreadMessage unreadMessage = generateUnreadMessageObject();
        String generatedUnreadMessageID = UUID.randomUUID().toString();

        Mockito.when(conversationGroupService.getSingleConversation(eq(createUnreadMessageRequest.getConversationId())))
                .thenReturn(conversationGroup);
        Mockito.when(unreadMessageRepoService.findByConversationGroupId(eq(createUnreadMessageRequest.getConversationId())))
                .thenReturn(Optional.empty());
        Mockito.when(unreadMessageRepoService.save(any())).thenAnswer(i -> {
            List<Object> objects = Arrays.asList(i.getArguments());
            UnreadMessage tobeSaved = (UnreadMessage) objects.get(0);
            tobeSaved.setId(generatedUnreadMessageID);
            return tobeSaved;
        });

        UnreadMessage savedUnreadMessage = unreadMessageService.addUnreadMessage(createUnreadMessageRequest);

        Mockito.verify(conversationGroupService).getSingleConversation(eq(createUnreadMessageRequest.getConversationId()));
        Mockito.verify(unreadMessageRepoService).findByConversationGroupId(eq(createUnreadMessageRequest.getConversationId()));
        Mockito.verify(unreadMessageRepoService).save(any());

        assertNotNull(savedUnreadMessage);
        assertNotNull(savedUnreadMessage.getId());
        assertEquals(savedUnreadMessage.getId(), generatedUnreadMessageID);
        assertEquals(savedUnreadMessage.getConversationId(), createUnreadMessageRequest.getConversationId());
        assertNotEquals(savedUnreadMessage.getConversationId(), unreadMessage.getConversationId());
        // Make sure the logic doesn't save from createUnreadMessageRequest's conversationGroupId.
        assertNotEquals(savedUnreadMessage.getConversationId(), conversationGroup.getId());
    }

    /**
     * Test UnreadMessageService.editUnreadMessage() with invalid conversation group ID.
     */
    @Test
    void testEditUnreadMessageWithInvalidConversationGroupId() {
        UpdateUnreadMessageRequest updateUnreadMessageRequest = generateUpdateUnreadMessageRequestObject();

        Mockito.when(conversationGroupService.getSingleConversation(eq(updateUnreadMessageRequest.getConversationId())))
                .thenThrow(new ConversationGroupNotFoundException(""));

        try {
            unreadMessageService.editUnreadMessage(updateUnreadMessageRequest);
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception exception) {
            Mockito.verify(conversationGroupService).getSingleConversation(eq(updateUnreadMessageRequest.getConversationId()));
            Mockito.verify(unreadMessageRepoService, times(0)).save(any());

            assertThat(exception).isInstanceOf(ConversationGroupNotFoundException.class);
        }
    }

    /**
     * Test UnreadMessageService.editUnreadMessage() with invalid conversation group ID.
     */
    @Test
    void testEditUnreadMessage() {
        UpdateUnreadMessageRequest updateUnreadMessageRequest = generateUpdateUnreadMessageRequestObject();
        ConversationGroup conversationGroup = generateConversationGroupObject();
        String generatedUnreadMessageID = UUID.randomUUID().toString();

        Mockito.when(conversationGroupService.getSingleConversation(eq(updateUnreadMessageRequest.getConversationId())))
                .thenReturn(conversationGroup);
        Mockito.when(unreadMessageRepoService.save(any())).thenAnswer(i -> {
            List<Object> objects = Arrays.asList(i.getArguments());
            UnreadMessage tobeSaved = (UnreadMessage) objects.get(0);
            tobeSaved.setId(generatedUnreadMessageID);
            return tobeSaved;
        });

        UnreadMessage editedUnreadMessage = unreadMessageService.editUnreadMessage(updateUnreadMessageRequest);

        Mockito.verify(conversationGroupService).getSingleConversation(eq(updateUnreadMessageRequest.getConversationId()));
        Mockito.verify(unreadMessageRepoService).save(any());

        assertNotNull(editedUnreadMessage);
        assertNotNull(editedUnreadMessage.getId());
        assertEquals(editedUnreadMessage.getId(), generatedUnreadMessageID);
        assertEquals(editedUnreadMessage.getConversationId(), editedUnreadMessage.getConversationId());
        // Make sure the logic doesn't save from createUnreadMessageRequest's conversationGroupId.
        assertNotEquals(editedUnreadMessage.getConversationId(), conversationGroup.getId());
    }

    /**
     * Test UnreadMessageService.deleteUnreadMessage() with invalid unreadMessage ID.
     */
    @Test
    void testDeleteUnreadMessageWithInvalidUnreadMessageId() {
        String unreadMessageId = UUID.randomUUID().toString();

        Mockito.when(unreadMessageRepoService.findById(eq(unreadMessageId)))
                .thenThrow(new UnreadMessageNotFoundException(""));

        try {
            unreadMessageService.deleteUnreadMessage(unreadMessageId);
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception exception) {
            Mockito.verify(unreadMessageRepoService).findById(eq(unreadMessageId));
            Mockito.verify(unreadMessageRepoService, times(0)).delete(any());

            assertThat(exception).isInstanceOf(UnreadMessageNotFoundException.class);
        }
    }

    /**
     * Test UnreadMessageService.deleteUnreadMessage() with correct unreadMessage ID.
     */
    @Test
    void testDeleteUnreadMessage() {
        String unreadMessageId = UUID.randomUUID().toString();
        UnreadMessage unreadMessage = generateUnreadMessageObject();

        Mockito.when(unreadMessageRepoService.findById(eq(unreadMessageId))).thenReturn(Optional.of(unreadMessage));

        unreadMessageService.deleteUnreadMessage(unreadMessageId);

        Mockito.verify(unreadMessageRepoService).findById(eq(unreadMessageId));
        Mockito.verify(unreadMessageRepoService).delete(any());
    }

    @Disabled
    @Test
    void testGetUserOwnUnreadMessages() {
        int numberOfConversationGroups = 10;
        List<ConversationGroup> conversationGroups = new ArrayList<>();
        List<UnreadMessage> unreadMessages = new ArrayList<>();

        for (int i = 0; i < numberOfConversationGroups; i++) {
            ConversationGroup conversationGroup = generateConversationGroupObject();
            conversationGroups.add(conversationGroup);

            UnreadMessage unreadMessage = generateUnreadMessageObject();
            unreadMessage.setConversationId(conversationGroup.getId());
            unreadMessages.add(unreadMessage);
        }

        Mockito.when(conversationGroupService.getUserOwnConversationGroups()).thenReturn(conversationGroups);
        Mockito.when(unreadMessageRepoService.findByConversationGroupId(argThat(i ->
                conversationGroups.stream().map(ConversationGroup::getId).collect(Collectors.toList()).contains(i))))
                .thenAnswer(i -> unreadMessages.stream().filter(unreadMessage ->
                        unreadMessage.getConversationId().equals(i.getArguments()[0]))
                        .findAny().orElseThrow(NullPointerException::new)
                );
        // userContacts.stream().filter(userContact -> userContact.getId().equals(i.getArguments()[0]))
        //                            .findAny().orElseThrow(NullPointerException::new)

        List<UnreadMessage> resultUnreadMessages = unreadMessageService.getUserOwnUnreadMessages();

        Mockito.verify(conversationGroupService).getUserOwnConversationGroups();
        Mockito.verify(unreadMessageRepoService, times(numberOfConversationGroups)).findByConversationGroupId(any());

        assertNotNull(resultUnreadMessages);
        assertEquals(resultUnreadMessages.size(), numberOfConversationGroups);
        assertEquals(resultUnreadMessages.size(), unreadMessages.size());
        assertEquals(resultUnreadMessages.size(), conversationGroups.size());

        assertTrue(resultUnreadMessages.containsAll(unreadMessages));
    }

    private CreateUnreadMessageRequest generateCreateUnreadMessageRequestObject() {
        return CreateUnreadMessageRequest.builder()
                .conversationId(UUID.randomUUID().toString())
                .lastMessage(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .count(0)
                .date(LocalDateTime.now())
                .build();
    }

    private UpdateUnreadMessageRequest generateUpdateUnreadMessageRequestObject() {
        return UpdateUnreadMessageRequest.builder()
                .conversationId(UUID.randomUUID().toString())
                .lastMessage(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .count(0)
                .date(LocalDateTime.now())
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
                .createdDate(LocalDateTime.now())
                .description(UUID.randomUUID().toString())
                .creatorUserId(memberIds.get(0))
                .notificationExpireDate(LocalDateTime.now())
                .block(false)
                .build();
    }

    private UnreadMessage generateUnreadMessageObject() {
        return UnreadMessage.builder()
                .conversationId(UUID.randomUUID().toString())
                .lastMessage(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .count(0)
                .date(LocalDateTime.now())
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
