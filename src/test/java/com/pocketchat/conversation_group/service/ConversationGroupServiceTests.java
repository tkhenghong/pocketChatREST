package com.pocketchat.conversation_group.service;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repo_services.conversation_group.ConversationGroupRepoService;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupRequest;
import com.pocketchat.models.controllers.request.user.CreateUserRequest;
import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import com.pocketchat.server.exceptions.user_contact.UserContactNotFoundException;
import com.pocketchat.services.chat_message.ChatMessageService;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import com.pocketchat.services.conversation_group.ConversationGroupServiceImpl;
import com.pocketchat.services.rabbitmq.RabbitMQService;
import com.pocketchat.services.user_contact.UserContactService;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

// https://www.infoworld.com/article/3537563/junit-5-tutorial-part-1-unit-testing-with-junit-5-mockito-and-hamcrest.html
@ExtendWith(MockitoExtension.class)
public class ConversationGroupServiceTests {

    @Mock
    ConversationGroupRepoService conversationGroupRepoService;

    @Mock
    ChatMessageService chatMessageService;

    @Mock
    UserContactService userContactService;

    @Mock
    RabbitMQService rabbitMQService;

    ConversationGroupService conversationGroupService;

    @BeforeEach
    void setUp() {
        conversationGroupService = new ConversationGroupServiceImpl(conversationGroupRepoService, chatMessageService, userContactService, rabbitMQService);
    }

    @Test
    @DisplayName("Conversation Group Service Test")
    public void testConversationGroupService() {
        System.out.println("Conversation Group Service Test Success.");
    }

    @Test
    @DisplayName("testAddConversationGroupWithoutUserContactsFound")
    public void testAddConversationGroupWithoutUserContactsFound() {
        CreateConversationGroupRequest createConversationGroupRequest = generateCreateConversationGroupRequest();
        try {
            System.out.println("userContactService: " + userContactService);
            System.out.println("conversationGroupService: " + conversationGroupService);
            Mockito.when(userContactService.getUserContact(eq(createConversationGroupRequest.getCreatorUserId())))
                    .thenThrow(UserContactNotFoundException.class);
            ConversationGroup conversationGroup = conversationGroupService.addConversation(createConversationGroupRequest);
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception exception) {
            Mockito.verify(userContactService).getUserContact(any());
            assertThat(exception).isInstanceOf(UserContactNotFoundException.class);
        }
    }

    private CreateUserRequest generateCreateUserRequestObject() {
        return CreateUserRequest.builder()
                .realName(UUID.randomUUID().toString())
                .displayName(UUID.randomUUID().toString())
                .countryCode(UUID.randomUUID().toString())
                .emailAddress(UUID.randomUUID().toString())
                .mobileNo(UUID.randomUUID().toString())
                .build();
    }

    private CreateConversationGroupRequest generateCreateConversationGroupRequest() {
        List<String> memberIds = generateRandomObjectIds(10);
        return CreateConversationGroupRequest.builder()
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

    private UserContact generateUserContactObject() {
        return UserContact.builder()
                .displayName(UUID.randomUUID().toString())
                .realName(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .userIds(new ArrayList<>())
                .userId(UUID.randomUUID().toString())
                .multimediaId(UUID.randomUUID().toString())
                .mobileNo(UUID.randomUUID().toString())
                .about(UUID.randomUUID().toString())
                .lastSeenDate(LocalDateTime.now())
                .block(false)
                .build();
    }

    private ConversationGroup generateConversationGroupObject() {
        List<String> memberIds = generateRandomObjectIds(10);

        return ConversationGroup.builder()
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

    private List<String> generateRandomObjectIds(Integer numberOfObjectIds) {
        List<String> objectIds = new ArrayList<>();
        for (int i = 0; i < numberOfObjectIds; i++) {
            objectIds.add(ObjectId.get().toHexString());
        }

        return objectIds;
    }
}
