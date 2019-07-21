package com.pocketchat.dbRepoServices.conversationGroup;

import com.pocketchat.dbRepositories.conversationGroup.ConversationGroupRepository;
import com.pocketchat.models.conversation_group.ConversationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationGroupRepoService {

    @Autowired
    private ConversationGroupRepository conversationGroupRepository;

    public ConversationGroupRepository getConversationGroupRepository() {
        return conversationGroupRepository;
    }

    public List<ConversationGroup> findByUserId(String userId) {
        Query query = new Query();
        return null;
    }
}
