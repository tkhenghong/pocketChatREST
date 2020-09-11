package com.pocketchat.db.repo_services.conversation_group;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.repositories.conversation_group.ConversationGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationGroupRepoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConversationGroupRepository conversationGroupRepository;

    @Autowired
    public ConversationGroupRepoService(ConversationGroupRepository conversationGroupRepository) {
        this.conversationGroupRepository = conversationGroupRepository;
    }

    public List<ConversationGroup> findAllConversationGroupsByIds(List<String> ids) {
        Iterable<ConversationGroup> conversationGroupIterable = conversationGroupRepository.findAllById(ids);

        // From Iterable to Collection List
        // https://stackoverflow.com/questions/6416706/easy-way-to-convert-iterable-to-collection
        List<ConversationGroup> conversationGroupList = new ArrayList<>();
        conversationGroupIterable.forEach(conversationGroupList::add);
        return conversationGroupList;
    }

    public Optional<ConversationGroup> findById(String conversationId) {
        return conversationGroupRepository.findById(conversationId);
    }

    // Find all conversationGroup objects by their memberIds array has the mentioned userContactId or not
    // Because 1 User has 1 UserContact, and the memberIds/adminIds of the ConversationGroup are userContactId
    public List<ConversationGroup> findAllByMemberIds(String userContactId) {
        return conversationGroupRepository.findAllByMemberIds(userContactId);
    }

    // Used for finding conversationGroup that has exact same member
    // For conversationGroup creation
    public List<ConversationGroup> findAllByMemberIds(List<String> memberIds) {
        return conversationGroupRepository.findAllByMemberIds(memberIds);
    }

    public ConversationGroup save(ConversationGroup conversationGroup) {
        return conversationGroupRepository.save(conversationGroup);
    }

    public void delete(ConversationGroup conversationGroup) {
        conversationGroupRepository.delete(conversationGroup);
    }
}
