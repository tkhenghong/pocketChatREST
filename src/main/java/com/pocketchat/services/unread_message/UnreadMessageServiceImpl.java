package com.pocketchat.services.unread_message;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.unread_message.UnreadMessage;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.models.user_authentication.UserAuthentication;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repo_services.unread_message.UnreadMessageRepoService;
import com.pocketchat.models.controllers.request.unread_message.CreateUnreadMessageRequest;
import com.pocketchat.models.controllers.request.unread_message.UpdateUnreadMessageRequest;
import com.pocketchat.models.controllers.response.unread_message.UnreadMessageResponse;
import com.pocketchat.server.exceptions.unread_message.UnreadMessageNotFoundException;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import com.pocketchat.services.user.UserService;
import com.pocketchat.services.user_authentication.UserAuthenticationService;
import com.pocketchat.services.user_contact.UserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnreadMessageServiceImpl implements UnreadMessageService {

    private final UnreadMessageRepoService unreadMessageRepoService;

    private final UserAuthenticationService userAuthenticationService;

    private final UserContactService userContactService;

    private final ConversationGroupService conversationGroupService;

    private final UserService userService;

    @Autowired
    public UnreadMessageServiceImpl(UnreadMessageRepoService unreadMessageRepoService,
                                    UserAuthenticationService userAuthenticationService,
                                    UserContactService userContactService,
                                    ConversationGroupService conversationGroupService,
                                    UserService userService) {
        this.unreadMessageRepoService = unreadMessageRepoService;
        this.userAuthenticationService = userAuthenticationService;
        this.userContactService = userContactService;
        this.conversationGroupService = conversationGroupService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public UnreadMessage addUnreadMessage(CreateUnreadMessageRequest createUnreadMessageRequest) {
        UnreadMessage unreadMessage = createUnreadMessageRequestToUnreadMessageMapper(createUnreadMessageRequest);

        // Check ConversationGroup's existence.
        conversationGroupService.getSingleConversation(unreadMessage.getConversationId());

        // Check any UnreadMessage with same ConversationGroup ID.
        Optional<UnreadMessage> unreadMessageOptional = unreadMessageRepoService.findByConversationGroupId(unreadMessage.getConversationId());

        return unreadMessageOptional.orElseGet(() -> unreadMessageRepoService.save(unreadMessage));
    }

    @Override
    @Transactional
    public UnreadMessage editUnreadMessage(UpdateUnreadMessageRequest updateUnreadMessageRequest) {
        getSingleUnreadMessage(updateUnreadMessageRequest.getId());

        // Check ConversationGroup's existence.
        conversationGroupService.getSingleConversation(updateUnreadMessageRequest.getConversationId());

        return unreadMessageRepoService.save(updateUnreadMessageRequestToUnreadMessageMapper(updateUnreadMessageRequest));
    }

    @Override
    @Transactional
    public void deleteUnreadMessage(String unreadMessageId) {
        unreadMessageRepoService.delete(getSingleUnreadMessage(unreadMessageId));
    }

    @Override
    public UnreadMessage getSingleUnreadMessage(String unreadMessageId) {
        Optional<UnreadMessage> unreadMessageOptional = unreadMessageRepoService.findById(unreadMessageId);
        if (unreadMessageOptional.isEmpty()) {
            throw new UnreadMessageNotFoundException("Unread Message not found, id:-" + unreadMessageId);
        }
        return unreadMessageOptional.get();
    }

    // UserContact --> ConversationGroups --> UnreadMessages
    // Reason to do this is because frontend don't have to resend requests. Just get do all of this in one go.
    @Override
    public List<UnreadMessage> getUserOwnUnreadMessages() {
        List<ConversationGroup> conversationGroupList = conversationGroupService.getUserOwnConversationGroups();
        return conversationGroupList.stream()
                .map(ConversationGroup::getId).collect(Collectors.toList())
                .stream().map(unreadMessageRepoService::findByConversationGroupId).collect(Collectors.toList())
                .stream().map(Optional::get).collect(Collectors.toList());
    }

    public UnreadMessage createUnreadMessageRequestToUnreadMessageMapper(CreateUnreadMessageRequest createUnreadMessageRequest) {
        return UnreadMessage.builder()
                .conversationId(createUnreadMessageRequest.getConversationId())
                .count(createUnreadMessageRequest.getCount())
                .date(createUnreadMessageRequest.getDate())
                .lastMessage(createUnreadMessageRequest.getLastMessage())
                .userId(createUnreadMessageRequest.getUserId())
                .build();
    }

    public UnreadMessage updateUnreadMessageRequestToUnreadMessageMapper(UpdateUnreadMessageRequest updateUnreadMessageRequest) {
        return UnreadMessage.builder()
                .id(updateUnreadMessageRequest.getId())
                .conversationId(updateUnreadMessageRequest.getConversationId())
                .count(updateUnreadMessageRequest.getCount())
                .date(updateUnreadMessageRequest.getDate())
                .lastMessage(updateUnreadMessageRequest.getLastMessage())
                .userId(updateUnreadMessageRequest.getUserId())
                .build();
    }

    public UnreadMessageResponse unreadMessageResponseMapper(UnreadMessage unreadMessage) {
        return UnreadMessageResponse.builder()
                .id(unreadMessage.getId())
                .conversationId(unreadMessage.getConversationId())
                .count(unreadMessage.getCount())
                .date(unreadMessage.getDate())
                .lastMessage(unreadMessage.getLastMessage())
                .userId(unreadMessage.getUserId())
                .build();
    }
}
