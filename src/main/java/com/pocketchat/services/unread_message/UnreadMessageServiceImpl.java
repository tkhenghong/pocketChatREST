package com.pocketchat.services.unread_message;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.unread_message.UnreadMessage;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repo_services.conversation_group.ConversationGroupRepoService;
import com.pocketchat.db.repo_services.unread_message.UnreadMessageRepoService;
import com.pocketchat.db.repo_services.user_contact.UserContactRepoService;
import com.pocketchat.models.controllers.request.unread_message.CreateUnreadMessageRequest;
import com.pocketchat.models.controllers.request.unread_message.UpdateUnreadMessageRequest;
import com.pocketchat.models.controllers.response.unread_message.UnreadMessageResponse;
import com.pocketchat.server.exceptions.unread_message.UnreadMessageNotFoundException;
import com.pocketchat.server.exceptions.user_contact.UserContactNotFoundException;
import com.pocketchat.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnreadMessageServiceImpl implements UnreadMessageService {

    private final UnreadMessageRepoService unreadMessageRepoService;

    private final UserContactRepoService userContactRepoService;

    private final ConversationGroupRepoService conversationGroupRepoService;

    private final UserService userService;

    @Autowired
    public UnreadMessageServiceImpl(UnreadMessageRepoService unreadMessageRepoService,
                                    UserContactRepoService userContactRepoService,
                                    ConversationGroupRepoService conversationGroupRepoService,
                                    UserService userService) {
        this.unreadMessageRepoService = unreadMessageRepoService;
        this.userContactRepoService = userContactRepoService;
        this.conversationGroupRepoService = conversationGroupRepoService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public UnreadMessageResponse addUnreadMessage(CreateUnreadMessageRequest createUnreadMessageRequest) {
        UnreadMessage unreadMessage = createUnreadMessageRequestToUnreadMessageMapper(createUnreadMessageRequest);
        // Find the unreadMessage that has the same conversationId in the DB
        Optional<UnreadMessage> unreadMessageOptional = unreadMessageRepoService.findByConversationGroupId(unreadMessage.getConversationId());
        return unreadMessageResponseMapper(unreadMessageOptional.orElseGet(() -> unreadMessageRepoService.save(unreadMessage)));
    }

    @Override
    @Transactional
    public UnreadMessageResponse editUnreadMessage(UpdateUnreadMessageRequest updateUnreadMessageRequest) {
        getSingleUnreadMessage(updateUnreadMessageRequest.getId());
        return unreadMessageResponseMapper(unreadMessageRepoService.save(updateUnreadMessageRequestToUnreadMessageMapper(updateUnreadMessageRequest)));
    }

    @Override
    @Transactional
    public void deleteUnreadMessage(String unreadMessageId) {
        unreadMessageRepoService.delete(getSingleUnreadMessage(unreadMessageId));
    }

    @Override
    public UnreadMessage getSingleUnreadMessage(String unreadMessageId) {
        Optional<UnreadMessage> unreadMessageOptional = unreadMessageRepoService.findById(unreadMessageId);
        if (!unreadMessageOptional.isPresent()) {
            throw new UnreadMessageNotFoundException("Unread Message not found, id:-" + unreadMessageId);
        }
        return unreadMessageOptional.get();
    }

    // User --> UserContact --> ConversationGroups --> UnreadMessages
    // Reason to do this is because frontend don't have to resend requests. Just get do all of this in one go.
    // TODO: Validate how fast this process is
    @Override
    public List<UnreadMessageResponse> getUnreadMessagesOfAUser(String userId) {
        // Identify user in DB
        User user = userService.getUser(userId);

        // Get UserContact of the User, validate it exists or not
        UserContact userContact = userContactRepoService.findByMobileNo(user.getMobileNo());

        if (ObjectUtils.isEmpty(userContact)) {
            throw new UserContactNotFoundException("Unread Message not found. userId:-" + userId);
        }

        // Using UserContact id, find all the conversationGroups that has this userContactId in memberIds field (ConversationGroup)
        List<ConversationGroup> conversationGroupList = conversationGroupRepoService.findAllByMemberIds(userContact.getId());

        // Get ConversationGroups' id, get UnreadMessage using ConversationGroup id, and retrieve those objects.
        List<UnreadMessage> unreadMessageList =
                conversationGroupList.stream()
                        .map(ConversationGroup::getId).collect(Collectors.toList())
                        .stream().map(unreadMessageRepoService::findByConversationGroupId).collect(Collectors.toList())
                        .stream().map(Optional::get).collect(Collectors.toList());

        return unreadMessageList.stream().map(this::unreadMessageResponseMapper).collect(Collectors.toList());
    }

    public UnreadMessage createUnreadMessageRequestToUnreadMessageMapper(CreateUnreadMessageRequest createUnreadMessageRequest) {
        return UnreadMessage.builder()
                .id(createUnreadMessageRequest.getId())
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
                .date(unreadMessage.getDate().getMillis())
                .lastMessage(unreadMessage.getLastMessage())
                .userId(unreadMessage.getUserId())
                .build();
    }
}
