package com.pocketchat.services.conversation_group.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repo_services.conversation_group.ConversationGroupRepoService;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupRequest;
import com.pocketchat.models.enums.chat_message.ChatMessageStatus;
import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import com.pocketchat.models.websocket.WebSocketMessage;
import com.pocketchat.server.configurations.websocket.WebSocketMessageSender;
import com.pocketchat.server.exceptions.conversation_group.ConversationGroupAdminNotInMemberIdListException;
import com.pocketchat.server.exceptions.conversation_group.CreatorIsNotConversationGroupMemberException;
import com.pocketchat.server.exceptions.user_contact.UserContactNotFoundException;
import com.pocketchat.services.chat_message.ChatMessageService;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import com.pocketchat.services.conversation_group.ConversationGroupServiceImpl;
import com.pocketchat.services.conversation_group_block.ConversationGroupBlockService;
import com.pocketchat.services.conversation_group_mute_notification.ConversationGroupMuteNotificationService;
import com.pocketchat.services.multimedia.MultimediaService;
import com.pocketchat.services.rabbitmq.RabbitMQService;
import com.pocketchat.services.unread_message.UnreadMessageService;
import com.pocketchat.services.user_contact.UserContactService;
import com.pocketchat.utils.file.FileUtil;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ConversationGroupServiceTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * This is a test case that only involves JUnit5, Mockito(From Jupiter), and Hamcrest.
     * Every test classes ONLY use @ExtendWith(MockitoExtension.class) and @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class),
     * DO NOT USE ANY OF THE FOLLOWING ANYMORE:
     * 1. @RunWith(SpringRunner.class)
     * 2a. @SpringBootTest
     * 2b. @SpringBootTest(**OR ANY SORT OF CONFIGURATIONS**)
     * 2c. @SpringBootTest(SpringBootTest.WebEnvironment.MOCK, class = Application.class)
     * 3. WebMvcTest (Probably not using it, since normally only service layer needs to be tested.)
     * 4. @AutoConfigureMockMvc
     * 5. @TestPropertySource(locations = "classpath:application-integrationtest.properties") (Not using unless other environments are needed)
     * 6. @Test from package org.junit;
     * 7. @Ignore from package org.junit;
     * 8. @MockBean coming from package org.springframework.boot.test.mock.mockito;
     * 9. Test lifecycle annotations such as @Before, @After from package org.junit;
     * <p>
     * For more, please visit: Migrate from JUnit4 from official documentation:
     * https://junit.org/junit5/docs/current/user-guide/#migrating-from-junit4
     * <p>
     * In this testing architecture, ONLY USE:
     * 1. Anything comes from org.junit.jupiter.api
     * 2. @ExtendWith(MockitoExtension.class)
     * 3. @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
     * 4. @Test from package org.junit.jupiter.api;
     * 5. @DisplayName from package org.junit.jupiter.api;
     * 6. @Disabled from package org.junit.jupiter.api;
     * 7. @DisplayNameGeneration from package org.junit.jupiter.api;
     * 8. @Mock from package org.mockito;
     * 9. @InjectMocks from package org.mockito, (NOTE: InjectMocks cannot mock Interfaces***)
     * Lifecycle of annotations of JUnit5 such as @BeforeEach, @AfterEach, @AfterAll.
     * Assertions from JUnit5 from package org.junit.jupiter.api.Assertions; Example: assertAll, assertEquals, assertNotNull, assertThrows, assertTrue and etc...
     * Optionally, you may use 3rd party such as Hamcrest to perform assertion that JUnit5 cannot do.
     * <p>
     * https://www.journaldev.com/21866/mockito-mock-examples
     * https://stackoverflow.com/questions/35325403/how-to-mock-an-inject-interface/35325581
     * <p>
     * Current Strategy of creating tests:
     * 1. Select any service class in service layer that you want to test on.
     * 2. Create a Test class like this class to test. organize your files properly in /test directory.
     * 3. Now this is important. You have to decide the strategy of Mocking. If:
     * 3a. Your target testing class is NOT interface and has variables annotated with @Value, go to Step 4.
     * 3b. Your target testing class is NOT interface and do NOT have any variables annotated with @Value, go to Step 7.
     * 3c. Your target testing class is interface and has variables annotated with @Value, go to Step 4.
     * 4. The Service Interface itself will NOT be annotated with any annotations.
     * 5. Then, for ALL dependencies that used Constructor Injection in the ServiceImpl class that implements
     * the Service Interface MUST be annotated with @Mock. Even the RepoService class has to be annotated with @Mock.
     * 6.And you need to have @BeforeEach void setup() {..}, write:
     * 6a. MockitoAnnotations.initMocks(this); // Initiate mocks to all dependencies.
     * 6b. Instantiate the Service Interface with the ServiceImpl with the constructor
     * that has all mocked dependencies in it. Go to Step .
     * <p>
     * 7. Your strategy is to only use @Mock @InjectMocks. You annotate the testing class with @InjectMocks and annotate
     * dependencies with @Mock. (Refer to ConversationGroupRepositoryTests2)
     * <p>
     * NOTE: Any unnecessary Mockito check will also make the test fail by throwing UnnecessaryStubbingException.
     */

    private ConversationGroupService conversationGroupService;

    @Mock
    private ConversationGroupBlockService conversationGroupBlockService;

    @Mock
    private ConversationGroupMuteNotificationService conversationGroupMuteNotificationService;

    @Mock
    private ConversationGroupRepoService conversationGroupRepoService;

    @Mock
    private ChatMessageService chatMessageService;

    @Mock
    private UserContactService userContactService;

    @Mock
    private UnreadMessageService unreadMessageService;

    @Mock
    private MultimediaService multimediaService;

    @Mock
    private RabbitMQService rabbitMQService;

    @Mock
    private WebSocketMessageSender webSocketMessageSender;

    @Mock
    private FileUtil fileUtil;

    @BeforeEach
    void setup() {
        //if we don't call below, we will get NullPointerException
        MockitoAnnotations.initMocks(this);
        conversationGroupService = new ConversationGroupServiceImpl(
                conversationGroupRepoService,
                conversationGroupBlockService,
                conversationGroupMuteNotificationService,
                chatMessageService,
                userContactService,
                unreadMessageService,
                multimediaService,
                rabbitMQService,
                webSocketMessageSender,
                fileUtil,
                new ObjectMapper());
    }

    @Test
    void testAddConversationGroupButOwnUserContactNotFound() {
        CreateConversationGroupRequest createConversationGroupRequest = generateCreateConversationGroupRequest();

        try {
            Mockito.when(userContactService.getOwnUserContact()).thenThrow(UserContactNotFoundException.class);
            conversationGroupService.addConversation(createConversationGroupRequest);
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception exception) {
            Mockito.verify(userContactService).getOwnUserContact();
            assertThat(exception).isInstanceOf(UserContactNotFoundException.class);
        }
    }

    @Test
    void testAddPersonalConversationGroupButAdminMembersAreEmpty() {
        CreateConversationGroupRequest createConversationGroupRequest = generateCreateConversationGroupRequest();
        UserContact creatorUserContact = generateUserContactObject();

        createConversationGroupRequest.setAdminMemberIds(new ArrayList<>());

        try {
            Mockito.when(userContactService.getOwnUserContact()).thenReturn(creatorUserContact);
            conversationGroupService.addConversation(createConversationGroupRequest);
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception exception) {
            Mockito.verify(userContactService).getOwnUserContact();
            assertThat(exception).isInstanceOf(CreatorIsNotConversationGroupMemberException.class);
        }
    }

    @Test
    void testAddPersonalConversationButGroupMembersAreEmpty() {
        CreateConversationGroupRequest createConversationGroupRequest = generateCreateConversationGroupRequest();
        UserContact creatorUserContact = generateUserContactObject();

        createConversationGroupRequest.setMemberIds(new ArrayList<>());

        try {
            Mockito.when(userContactService.getOwnUserContact()).thenReturn(creatorUserContact);
            conversationGroupService.addConversation(createConversationGroupRequest);
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception exception) {
            Mockito.verify(userContactService).getOwnUserContact();
            assertThat(exception).isInstanceOf(CreatorIsNotConversationGroupMemberException.class);
        }
    }

    @Test
    void testCreatePersonalConversationGroupButAdminIsNotWithinMembersAndAdmins() {
        int numberOfGroupMembers = 20;
        UserContact creatorUserContact = generateUserContactObject();
        ConversationGroup conversationGroup = generateConversationGroupObject();
        UserContact unknownUserContact = generateUserContactObject();

        List<UserContact> userContacts = new ArrayList<>();

        for (int i = 0; i < numberOfGroupMembers; i++) {
            userContacts.add(generateUserContactObject());
        }

        CreateConversationGroupRequest createConversationGroupRequest = generateCreateConversationGroupRequest();
        createConversationGroupRequest.setMemberIds(userContacts.stream().map(UserContact::getId).collect(Collectors.toList()));
        createConversationGroupRequest.setAdminMemberIds(Collections.singletonList(unknownUserContact.getId())); // Empty or wrong value

        conversationGroup.setMemberIds(createConversationGroupRequest.getMemberIds());
        conversationGroup.setAdminMemberIds(Collections.singletonList(unknownUserContact.getId())); // Empty or wrong value

        try {
            Mockito.when(userContactService.getOwnUserContact()).thenReturn(creatorUserContact);
            conversationGroupService.addConversation(createConversationGroupRequest);
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception exception) {
            Mockito.verify(userContactService).getOwnUserContact();
            Mockito.verify(userContactService, times(0)).getUserContact(any());
            Mockito.verify(conversationGroupRepoService, times(0)).save(any());

            assertThat(exception).isInstanceOf(CreatorIsNotConversationGroupMemberException.class);
        }
    }

    @Test
    void testCreatePersonalConversationGroupButSomeAdminIdsNotWithinMemberIds() {
        int numberOfGroupMembers = 20;
        UserContact creatorUserContact = generateUserContactObject();
        ConversationGroup conversationGroup = generateConversationGroupObject();
        UserContact unknownUserContact = generateUserContactObject();

        List<UserContact> userContacts = new ArrayList<>();

        for (int i = 0; i < numberOfGroupMembers; i++) {
            userContacts.add(generateUserContactObject());
        }

        List<String> memberUserContactStrings = userContacts.stream().map(UserContact::getId).collect(Collectors.toList());
        userContacts.add(creatorUserContact);
        memberUserContactStrings.add(creatorUserContact.getId()); // Add creatorUserContact ID to member list, but not admin member list.

        // Merge 2 lists: https://stackoverflow.com/questions/189559/how-do-i-join-two-lists-in-java
        List<String> userContactStringsWithForeignUserContact = Stream.concat(memberUserContactStrings.stream(),
                Stream.of(unknownUserContact.getId())).collect(Collectors.toList());
        userContacts.add(unknownUserContact);

        CreateConversationGroupRequest createConversationGroupRequest = generateCreateConversationGroupRequest();
        createConversationGroupRequest.setMemberIds(memberUserContactStrings);
        createConversationGroupRequest.setAdminMemberIds(userContactStringsWithForeignUserContact);

        conversationGroup.setMemberIds(memberUserContactStrings);
        conversationGroup.setAdminMemberIds(userContactStringsWithForeignUserContact);

        try {
            Mockito.when(userContactService.getOwnUserContact()).thenReturn(creatorUserContact);
            Mockito.when(userContactService.getUserContact(argThat(t ->
                    createConversationGroupRequest.getMemberIds().contains(t)))).thenAnswer(i ->
                    // In .thenAnswer(), you can get the arguments inside the Mockito.when(the method that you are mocking in mockBean.mockMethodName)
                    // If the mocking method has 1 argument, then i.getArguments() will have size of 1, and vice versa.
                    // The arguments are Objects.class, you just have to cast to the correct ones to get the argument out.
                    userContacts.stream().filter(userContact -> userContact.getId().equals(i.getArguments()[0]))
                            .findAny().orElseThrow(NullPointerException::new)
            );

            conversationGroupService.addConversation(createConversationGroupRequest);
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception exception) {
            Mockito.verify(userContactService).getOwnUserContact();
            int totalNumberOfUserContacts = createConversationGroupRequest.getAdminMemberIds().size() +
                    createConversationGroupRequest.getMemberIds().size();
            Mockito.verify(userContactService, times(totalNumberOfUserContacts)).getUserContact(any());
            Mockito.verify(conversationGroupRepoService, times(0)).save(any());

            assertThat(exception).isInstanceOf(ConversationGroupAdminNotInMemberIdListException.class);
        }
    }

    @Test
    void testCreatePersonalConversationGroup() {
        int numberOfGroupMembers = 20;
        String conversationGroupId = UUID.randomUUID().toString();
        UserContact creatorUserContact = generateUserContactObject();
        ChatMessage chatMessage = generateChatMessage();
        WebSocketMessage webSocketMessage = generateWebSocketMessage();
        webSocketMessage.setChatMessage(chatMessage);

        List<UserContact> userContacts = new ArrayList<>();

        for (int i = 0; i < numberOfGroupMembers; i++) {
            userContacts.add(generateUserContactObject());
        }

        List<String> memberUserContactStrings = userContacts.stream().map(UserContact::getId).collect(Collectors.toList());
        userContacts.add(creatorUserContact);
        memberUserContactStrings.add(creatorUserContact.getId()); // Add creatorUserContact ID to member list, but not admin member list.

        int finalNumberOfGroupMembers = userContacts.size();

        CreateConversationGroupRequest createConversationGroupRequest = generateCreateConversationGroupRequest();
        createConversationGroupRequest.setMemberIds(memberUserContactStrings);
        createConversationGroupRequest.setAdminMemberIds(memberUserContactStrings);

        Mockito.when(userContactService.getOwnUserContact()).thenReturn(creatorUserContact);
        Mockito.when(userContactService.getUserContact(argThat(t ->
                createConversationGroupRequest.getMemberIds().contains(t)))).thenAnswer(i ->
                userContacts.stream().filter(userContact ->
                        userContact.getId().equals(Arrays.asList(i.getArguments()).get(0)))
                        .findAny().orElseThrow(NullPointerException::new)
        );
        Mockito.when(conversationGroupRepoService.save(any())).thenAnswer(i -> {
            ConversationGroup toBeSaved = (ConversationGroup) Arrays.asList(i.getArguments()).get(0);
            toBeSaved.setId(conversationGroupId);
            return toBeSaved;
        });
        Mockito.when(chatMessageService.addChatMessage(any())).thenReturn(chatMessage);

        ConversationGroup savedConversationGroup = conversationGroupService.addConversation(createConversationGroupRequest);

        Mockito.verify(userContactService).getOwnUserContact();
        int totalNumberOfUserContacts = createConversationGroupRequest.getAdminMemberIds().size() +
                createConversationGroupRequest.getMemberIds().size();
        Mockito.verify(userContactService, times(totalNumberOfUserContacts)).getUserContact(any());
        Mockito.verify(conversationGroupRepoService).save(any());
        Mockito.verify(chatMessageService, times(2)).addChatMessage(any());

        // 4 messages is sent to RabbitMQ for creating a new conversation group.
        Mockito.verify(rabbitMQService, times(finalNumberOfGroupMembers * 4)).addMessageToQueue(anyString(), anyString(), anyString(), anyString());

        assertNotNull(savedConversationGroup);
        assertEquals(savedConversationGroup.getId(), conversationGroupId);
    }

    private CreateConversationGroupRequest generateCreateConversationGroupRequest() {
        List<String> memberIds = generateRandomObjectIds(10);
        return CreateConversationGroupRequest.builder()
                .conversationGroupType(ConversationGroupType.Group)
                .name(UUID.randomUUID().toString())
                .memberIds(memberIds)
                .adminMemberIds(Collections.singletonList(memberIds.get(0)))
                .description(UUID.randomUUID().toString())
                .build();
    }

    private UserContact generateUserContactObject() {
        return UserContact.builder()
                .id(UUID.randomUUID().toString())
                .displayName(UUID.randomUUID().toString())
                .realName(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .userIds(new ArrayList<>())
                .userId(UUID.randomUUID().toString())
                .profilePicture(UUID.randomUUID().toString())
                .mobileNo(UUID.randomUUID().toString())
                .countryCode(UUID.randomUUID().toString())
                .about(UUID.randomUUID().toString())
                .build();
    }

    private ConversationGroup generateConversationGroupObject() {
        List<String> memberIds = generateRandomObjectIds(10);

        return ConversationGroup.builder()
                .id(UUID.randomUUID().toString())
                .conversationGroupType(ConversationGroupType.Group)
                .name(UUID.randomUUID().toString())
                .groupPhoto(UUID.randomUUID().toString())
                .memberIds(memberIds)
                .adminMemberIds(Collections.singletonList(memberIds.get(0)))
                .description(UUID.randomUUID().toString())
                .creatorUserId(memberIds.get(0))
                .build();
    }

    private ChatMessage generateChatMessage() {
        return ChatMessage.builder()
                .id(UUID.randomUUID().toString())
                .conversationId(UUID.randomUUID().toString())
                .chatMessageStatus(ChatMessageStatus.Sent)
                .messageContent(UUID.randomUUID().toString())
                .multimediaId(UUID.randomUUID().toString())
                .senderId(UUID.randomUUID().toString())
                .senderMobileNo(UUID.randomUUID().toString())
                .senderName(UUID.randomUUID().toString())
                .build();
    }

    private WebSocketMessage generateWebSocketMessage() {
        return WebSocketMessage.builder().build();
    }

    private List<String> generateRandomObjectIds(Integer numberOfObjectIds) {
        List<String> objectIds = new ArrayList<>();
        for (int i = 0; i < numberOfObjectIds; i++) {
            objectIds.add(ObjectId.get().toHexString());
        }

        return objectIds;
    }
}
