package com.pocketchat.conversation_group.service;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupRequest;
import com.pocketchat.models.controllers.request.user.CreateUserRequest;
import com.pocketchat.models.controllers.response.conversation_group.ConversationGroupResponse;
import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import com.pocketchat.services.user.UserService;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConversationGroupTests {

    @Autowired
    ConversationGroupService conversationGroupService;

    @Autowired
    UserService userService;

    @Test
    public void testAddConversationGroup() {
        ConversationGroupResponse conversationGroupResponse = conversationGroupService.addConversation(generateCreateConversationGroupRequest());
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

    private ConversationGroup generateConversationGroupObject(Integer numberOfMembers, Integer numberOfAdmins) {
        List<String> memberIds = generateRandomObjectIds(numberOfMembers);

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
