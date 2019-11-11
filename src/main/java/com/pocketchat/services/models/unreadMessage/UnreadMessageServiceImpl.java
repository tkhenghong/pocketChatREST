package com.pocketchat.services.models.unreadMessage;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.unread_message.UnreadMessage;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repoServices.conversationGroup.ConversationGroupRepoService;
import com.pocketchat.db.repoServices.unreadMessage.UnreadMessageRepoService;
import com.pocketchat.db.repoServices.userContact.UserContactRepoService;
import com.pocketchat.server.exceptions.unreadMessage.UnreadMessageNotFoundException;
import com.pocketchat.server.exceptions.userContact.UserContactNotFoundException;
import com.pocketchat.services.models.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public UnreadMessage addUnreadMessage(UnreadMessage unreadMessage) {
        // Find the unreadMessage that has the same conversationId in the DB
        Optional<UnreadMessage> unreadMessageOptional = unreadMessageRepoService.findByConversationGroupId(unreadMessage.getConversationId());
        return unreadMessageOptional.orElseGet(() -> unreadMessageRepoService.save(unreadMessage));
    }

    @Override
    public void editUnreadMessage(UnreadMessage unreadMessage) {
        getSingleMultimedia(unreadMessage.getId());
        unreadMessageRepoService.save(unreadMessage);
    }

    @Override
    public void deleteUnreadMessage(String unreadMessageId) {
        unreadMessageRepoService.delete(getSingleMultimedia(unreadMessageId));
    }

    @Override
    public UnreadMessage getSingleMultimedia(String unreadMessageId) {
        Optional<UnreadMessage> unreadMessageOptional = unreadMessageRepoService.findById(unreadMessageId);
        return validateUnreadMessageNotFound(unreadMessageOptional, unreadMessageId);
    }

    // User --> UserContact --> ConversationGroups --> UnreadMessages
    // Reason to do this is because frontend don't have to resend requests. Just get do all of this in one go.
    // TODO: Validate how fast this process is
    @Override
    public List<UnreadMessage> getUnreadMessagesOfAUser(String userId) {
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

        return unreadMessageList;
    }

    private UnreadMessage validateUnreadMessageNotFound(Optional<UnreadMessage> unreadMessageOptional, String unreadMessageId) {
        if (!unreadMessageOptional.isPresent()) {
            throw new UnreadMessageNotFoundException("Unread Message not found, id:-" + unreadMessageId);
        } else {
            return unreadMessageOptional.get();
        }
    }
}
