package com.pocketchat.dbRepoServices.conversationGroup;

import com.pocketchat.dbRepositories.conversationGroup.ConversationGroupRepository;
import com.pocketchat.models.conversation_group.ConversationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversationGroupRepoService {

    @Autowired
    private ConversationGroupRepository conversationGroupRepository;

    public List<ConversationGroup> findByUserId(String userId) {
        Query query = new Query();
        return null;
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
