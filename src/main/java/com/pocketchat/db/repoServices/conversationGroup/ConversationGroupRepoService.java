package com.pocketchat.db.repoServices.conversationGroup;

import com.pocketchat.db.repositories.conversationGroup.ConversationGroupRepository;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationGroupRepoService {

    @Autowired
    private ConversationGroupRepository conversationGroupRepository;

    public List<ConversationGroup> findAllConversationGroupsByGroupMemberId(List<String> groupMemberIds) {
        Iterable<String> iterable = groupMemberIds;
        Iterable<ConversationGroup> conversationGroupIterable = conversationGroupRepository.findAllById(iterable);

        // TODO: Need expert review for possibly better options
        // From Iterable to Collection List
        // https://stackoverflow.com/questions/6416706/easy-way-to-convert-iterable-to-collection
        List<ConversationGroup> conversationGroupList = new ArrayList<>();
        conversationGroupIterable.forEach(conversationGroupList::add);
        return conversationGroupList;
    }

    public Optional<ConversationGroup> findById(String conversationId) {
        return conversationGroupRepository.findById(conversationId);
    }

    //deleteById


    public ConversationGroup save(ConversationGroup conversationGroup) {
        return conversationGroupRepository.save(conversationGroup);
    }

    public void delete(ConversationGroup conversationGroup) {
        conversationGroupRepository.delete(conversationGroup);
    }
}
