package com.pocketchat.conversation_group.service;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repo_services.user_contact.UserContactRepoService;
import com.pocketchat.db.repositories.conversation_group.ConversationGroupRepository;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupRequest;
import com.pocketchat.models.controllers.request.user.CreateUserRequest;
import com.pocketchat.models.controllers.response.conversation_group.ConversationGroupResponse;
import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import com.pocketchat.server.exceptions.user_contact.UserContactNotFoundException;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import com.pocketchat.services.user.UserService;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

// Reference: https://www.baeldung.com/spring-boot-testing
// https://mkyong.com/spring-boot/spring-boot-junit-5-mockito/
// https://spring.io/guides/gs/testing-web/
// https://www.javaworld.com/article/3537563/junit-5-tutorial-part-1-unit-testing-with-junit-5-mockito-and-hamcrest.html
// https://www.baeldung.com/mockito-annotations
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ConversationGroupServiceTests {

    @Autowired
    ConversationGroupService conversationGroupService;

    @Autowired
    ConversationGroupRepository conversationGroupRepository;

    @Autowired
    UserContactRepoService userContactRepoService;

    @Autowired
    UserService userService;

    @Test
    @DisplayName("Conversation Group Service Test")
    public void testConversationGroupService() {
        System.out.println("Conversation Group Service Test Success.");
    }

    @Test
    @DisplayName("testAddConversationGroupWithoutUserContactsFound")
    public void testAddConversationGroupWithoutUserContactsFound() {
        CreateConversationGroupRequest createConversationGroupRequest = generateCreateConversationGroupRequest();
        UserContact adminUserContact = generateUserContactObject();
        Exception exception = assertThrows(UserContactNotFoundException.class, () -> {
            //  Mockito.when(userContactRepoService.findById(eq(createConversationGroupRequest.getCreatorUserId()))).thenReturn(Optional.of(adminUserContact));
            ConversationGroupResponse conversationGroupResponse = conversationGroupService.addConversation(createConversationGroupRequest);
            //  Mockito.verify(userContactRepoService.findById(eq(createConversationGroupRequest.getCreatorUserId())));
        });
    }

    private CreateUserRequest generateCreateUserRequestObject() {
        return CreateUserRequest.builder()
                .realName(UUID.randomUUID().toString())
                .displayName(UUID.randomUUID().toString())
                .countryCode(UUID.randomUUID().toString())
                .effectivePhoneNumber(UUID.randomUUID().toString())
                .emailAddress(UUID.randomUUID().toString())
                .mobileNo(UUID.randomUUID().toString())
                .googleAccountId(UUID.randomUUID().toString())
                .build();
    }

    private CreateConversationGroupRequest generateCreateConversationGroupRequest() {
        List<String> memberIds = generateRandomObjectIds(10);
        return CreateConversationGroupRequest.builder()
                .conversationGroupType(ConversationGroupType.Group)
                .name(UUID.randomUUID().toString())
                .memberIds(memberIds)
                .adminMemberIds(Collections.singletonList(memberIds.get(0)))
                .createdDate(new DateTime())
                .description(UUID.randomUUID().toString())
                .creatorUserId(memberIds.get(0))
                .notificationExpireDate(new DateTime())
                .block(false)
                .build();
    }

    private UserContact generateUserContactObject() {
        return UserContact.builder()
                .displayName(UUID.randomUUID().toString())
                .realName(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .userIds(Arrays.asList())
                .userId(UUID.randomUUID().toString())
                .multimediaId(UUID.randomUUID().toString())
                .mobileNo(UUID.randomUUID().toString())
                .about(UUID.randomUUID().toString())
                .lastSeenDate(new DateTime())
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
                .createdDate(new DateTime())
                .description(UUID.randomUUID().toString())
                .creatorUserId(memberIds.get(0))
                .notificationExpireDate(new DateTime())
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
