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
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

/**
 * TODO: Interesting articles:
 * https://www.infoworld.com/article/3537563/junit-5-tutorial-part-1-unit-testing-with-junit-5-mockito-and-hamcrest.html
 * https://www.infoworld.com/article/3543268/junit-5-tutorial-part-2-unit-testing-spring-mvc-with-junit-5.html
 * <p>
 * This test is using the method that is used in the article above(Part 2) in Repository level test.
 * Short notes in this test:
 * 1. @SpringBootTest already includes @ExtendWith(SpringExtension.class)
 * <p>
 * Observation:
 * 1. This testing architecture takes longer to finish than method 2. It has to start Spring Boot app to load up all
 * Spring Context before even start testing.
 * 2. This test doesn't require me to do @BeforeEach void setup() {..}, which means I have to initialize mocks manually
 * and insert all mocking dependencies within the service in order to make the test work.
 * 3.
 * 4. If you perform Mockito.when unnecessary methods in the test cases, it will NOT cause UnnecessaryStubbingException.
 */
@Disabled
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ConversationGroupRepositoryTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @MockBean
    ConversationGroupRepository conversationGroupRepository;

    @Autowired
    ConversationGroupRepoService conversationGroupRepoService;

    // This is used for testing @Value will work in this method or not. In this Method 1 this will work.
    // @Value("${encryption.rsa.public.key.directory}")
    // private String rsaPublicKeyDirectory;

    @Test
    void testConversationGroupRepoService() {
        ConversationGroup entity = generateConversationGroupObject();

        // If uncomment this below will NOT cause UnnecessaryStubbingException.
        // Mockito.when(conversationGroupRepoService.findById(anyString())).thenReturn(Optional.of(entity));

        Mockito.when(conversationGroupRepoService.save(eq(entity))).thenAnswer(i -> i.getArguments()[0]);

        ConversationGroup conversationGroup = conversationGroupRepoService.save(entity);
        // NOTE: Read README.md for testing.
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
                .description(UUID.randomUUID().toString())
                .creatorUserId(memberIds.get(0))
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
