package com.pocketchat.services.conversation_group.repository;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.repo_services.conversation_group.ConversationGroupRepoService;
import com.pocketchat.db.repositories.conversation_group.ConversationGroupRepository;
import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;

// https://www.infoworld.com/article/3537563/junit-5-tutorial-part-1-unit-testing-with-junit-5-mockito-and-hamcrest.html
@ExtendWith(MockitoExtension.class)
public class ConversationGroupRepositoryTests {

    @Mock
    ConversationGroupRepository conversationGroupRepository;

    @InjectMocks
    ConversationGroupRepoService conversationGroupRepoService;

    @Test
    @DisplayName("Conversation Group Repository Test")
    public void testConversationGroupRepository() {
        System.out.println("Conversation Group Repository Test Success.");
    }

    @Test
    @DisplayName("testConversationGroupRepoService")
    public void testConversationGroupRepoService() {

        ConversationGroup entity = generateConversationGroupObject();

        Mockito.when(conversationGroupRepoService.save(eq(entity))).thenReturn(entity);

        ConversationGroup conversationGroup = conversationGroupRepoService.save(entity);
        assertNotNull(conversationGroup);
        assertEquals(entity.getName(), conversationGroup.getName());
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
