package com.pocketchat.services.models.conversationGroup;

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
import java.util.stream.Collectors;

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
        if (conversationGroup.getType().equals("Personal")) {
            // 1. Find a list of conversationGroup that has same memberIds
            List<ConversationGroup> conversationGroupList = conversationGroupRepoService.findByMemberIdsContaining(conversationGroup.getMemberIds());
            // 2. Filter to get the Personal ConversationGroup
            List<ConversationGroup> personalConversationGroupList = conversationGroupList
                    .stream().filter((ConversationGroup conversationGroup1) -> conversationGroup1.getType().equals("Personal")
                            && conversationGroup1.getAdminMemberIds().equals(conversationGroup.getAdminMemberIds())
                            && conversationGroup1.getName().equals(conversationGroup.getName())).collect(Collectors.toList());
            // 3. Should found the exact group
            if (personalConversationGroupList.size() == 1) {
                return personalConversationGroupList.iterator().next();
            } else if (personalConversationGroupList.isEmpty()) { // Must be 0 conversationGroup
                return conversationGroupRepoService.save(conversationGroup);
            } else {
                return null;
            }
        } else {
            System.out.println("ConversationGroupServiceImpl.java if (conversationGroup.getType().equals(\"Group/Broadcast\"))");
            // Group/Broadcast
            return conversationGroupRepoService.save(conversationGroup);
        }
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
        System.out.println("ConversationGroupServiceImpl.java getConversationsForUser()");
        // Retreive conversations for the user
        List<UserContact> userContactList = userContactRepoService.findByUserIdsContaining(userId);
        if (userContactList.isEmpty()) {
            throw new UserContactNotFoundException("UserContact not found: " + userId);
        }
        System.out.println("userContactList.size(): " + userContactList.size());
        List<ConversationGroup> conversationGroupList = new ArrayList<>();
        userContactList.forEach((UserContact userContact) -> conversationGroupList.addAll(findByMemberIdsContaining(userContact.getId())));
        System.out.println("conversationGroupList.size(): " + conversationGroupList.size());
        conversationGroupList.forEach(this::printConversationGroupDetails);
        return conversationGroupList;
    }

    private void printConversationGroupDetails(ConversationGroup conversationGroup) {
        System.out.println("conversationGroup.getId(): " + conversationGroup.getId());
        System.out.println("conversationGroup.getName(): " + conversationGroup.getName());
        System.out.println("conversationGroup.getType(): " + conversationGroup.getType());
        System.out.println("conversationGroup.getDescription(): " + conversationGroup.getDescription());

        conversationGroup.getMemberIds().forEach((String memberId) -> System.out.println("memberId: " + memberId));
        conversationGroup.getAdminMemberIds().forEach((String adminMemberId) -> System.out.println("adminMemberId: " + adminMemberId));
    }

    @Override
    public List<ConversationGroup> findByMemberIdsContaining(String userContactId) {
        List<ConversationGroup> conversationGroupList = conversationGroupRepoService.findByMemberIdsContaining(userContactId);
        if (conversationGroupList.isEmpty()) {
            throw new UserContactNotFoundException("UserContactId not found: " + userContactId);
        }
        return conversationGroupList;
    }

    // findByMemberIdsContaining(userContact.id)

    private ConversationGroup validateConversationGroupNotFound(Optional<ConversationGroup> conversationGroupOptional, String conversationGroupId) {
        if (!conversationGroupOptional.isPresent()) {
            throw new ConversationGroupNotFoundException("conversationGroupId-" + conversationGroupId);
        } else {
            return conversationGroupOptional.get();
        }
    }
}
