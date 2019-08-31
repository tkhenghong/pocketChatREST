package com.pocketchat.services.conversationGroup;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repoServices.conversationGroup.ConversationGroupRepoService;
import com.pocketchat.db.repoServices.userContact.UserContactRepoService;
import com.pocketchat.server.exceptions.conversationGroup.ConversationGroupNotFoundException;
import com.pocketchat.server.exceptions.userContact.UserContactNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationGroupServiceImpl implements ConversationGroupService {

    private final ConversationGroupRepoService conversationGroupRepoService;

    private final UserContactRepoService userContactRepoService;

    // Avoid Field Injection
    @Autowired
    public ConversationGroupServiceImpl(ConversationGroupRepoService conversationGroupRepoService, UserContactRepoService userContactRepoService) {
        this.conversationGroupRepoService = conversationGroupRepoService;
        this.userContactRepoService = userContactRepoService;
    }

    @Override
    public ConversationGroup addConversation(ConversationGroup conversationGroup) {
        return conversationGroupRepoService.save(conversationGroup);
    }

    @Override
    public void editConversation(ConversationGroup conversationGroup) {
        getSingleConversation(conversationGroup.getId());
        addConversation(conversationGroup);
    }

    @Override
    public void deleteConversation(String conversationGroupId) {
        conversationGroupRepoService.delete(getSingleConversation(conversationGroupId));
    }

    @Override
    public ConversationGroup getSingleConversation(String conversationGroupId) {
        Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.findById(conversationGroupId);
        return validateConversationGroupNotFound(conversationGroupOptional, conversationGroupId);
    }

    @Override
    public List<ConversationGroup> getConversationsForUser(String userId) {
        List<UserContact> userContactList = userContactRepoService.findByUserId(userId);
        if (userContactList.isEmpty()) {
            throw new UserContactNotFoundException("UserContact not found: " + userId);
        }
        List<String> conversationIds = new ArrayList<>();
        userContactList.forEach((UserContact userContact) -> conversationIds.add(userContact.getConversationId()));
        List<ConversationGroup> conversationGroupList = conversationGroupRepoService.findAllConversationGroupsByIds(conversationIds);
        return conversationGroupList;
    }

    private ConversationGroup validateConversationGroupNotFound(Optional<ConversationGroup> conversationGroupOptional, String conversationGroupId) {
        if (!conversationGroupOptional.isPresent()) {
            throw new ConversationGroupNotFoundException("conversationGroupId-" + conversationGroupId);
        } else {
            return conversationGroupOptional.get();
        }
    }
}
