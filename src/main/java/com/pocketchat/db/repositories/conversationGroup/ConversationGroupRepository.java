package com.pocketchat.db.repositories.conversationGroup;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConversationGroupRepository extends MongoRepository<ConversationGroup, String> {
    List<ConversationGroup> findByMemberIdsContaining(String userContactId);
    List<ConversationGroup> findByMemberIdsContaining(List<String> memberIds);
}
