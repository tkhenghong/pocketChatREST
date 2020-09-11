package com.pocketchat.services.unread_message.service;

import com.pocketchat.db.repo_services.unread_message.UnreadMessageRepoService;
import com.pocketchat.models.controllers.request.unread_message.CreateUnreadMessageRequest;
import com.pocketchat.server.exceptions.conversation_group.ConversationGroupNotFoundException;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import com.pocketchat.services.unread_message.UnreadMessageService;
import com.pocketchat.services.unread_message.UnreadMessageServiceImpl;
import com.pocketchat.services.user.UserService;
import com.pocketchat.services.user_authentication.UserAuthenticationService;
import com.pocketchat.services.user_contact.UserContactService;
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

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UnreadMessageServiceTests {

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
        } catch (Exception exception) {
            Mockito.verify(conversationGroupService).getSingleConversation(eq(createUnreadMessageRequest.getConversationId()));

            assertThat(exception).isInstanceOf(ConversationGroupNotFoundException.class);
        }
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
}
