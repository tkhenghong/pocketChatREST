package com.pocketchat.conversation_group.repository;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.repositories.conversation_group.ConversationGroupRepository;
import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

// Reference: https://www.baeldung.com/spring-boot-testing
// https://mkyong.com/spring-boot/spring-boot-junit-5-mockito/
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ConversationGroupRepositoryTests {

    @Autowired
    ConversationGroupRepository conversationGroupRepository;

    @Test
    @DisplayName("Conversation Group Repository Test")
    public void testConversationGroupRepository() {
        System.out.println("Conversation Group Repository Test Success.");
    }

    @Test
    @DisplayName("testConversationGroupRepoService")
    public void testConversationGroupRepoService() {

        ConversationGroup entity = generateConversationGroupObject();

        ConversationGroup conversationGroup = conversationGroupRepository.save(entity);
        Assertions.assertNotNull(conversationGroup);
        Assertions.assertEquals(entity.getName(), conversationGroup.getName());
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
