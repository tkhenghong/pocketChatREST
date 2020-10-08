package com.pocketchat.services.conversation_group.repository;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.repo_services.conversation_group.ConversationGroupRepoService;
import com.pocketchat.db.repositories.conversation_group.ConversationGroupRepository;
import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;

/**
 * This is the 2nd method that is devised in Repository level test.
 * 1. This testing architecture is faster than the Method 1.
 * 2. This method requires to insert dependencies and values manually in @BeforeEach void setup() {..}
 * 3. If your testing Service have values within it with @Value annotation, you have to change your style to
 * * constructor injection, which injects all values when @Service class is created.
 * (@InjectMocks is able to mock a class but not interface)
 * (If use constructor injection, you don't have to use @InjectMocks anymore)
 * (@InjectMocks will ONLY work when there's no @Value need to be stubbed for the target testing service.)
 * 4. If you perform Mockito.when unnecessary methods in the test cases, it will cause UnnecessaryStubbingException.
 */
@Disabled
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ConversationGroupRepositoryTests2 {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Mock
    ConversationGroupRepository conversationGroupRepository;

    @InjectMocks
    ConversationGroupRepoService conversationGroupRepoService;

    // This is used for testing @Value will work in this method or not. In this Method 2 this will NOT work.
    //    @Value("${encryption.rsa.public.key.directory}")
    //    private String rsaPublicKeyDirectory;

    @Test
    void testConversationGroupRepoService() {
        ConversationGroup entity = generateConversationGroupObject();

        // If uncomment this below will cause UnnecessaryStubbingException.
        // Mockito.when(conversationGroupRepoService.findById(anyString())).thenReturn(Optional.of(entity));

        Mockito.when(conversationGroupRepoService.save(eq(entity))).thenAnswer(i -> i.getArguments()[0]);

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
