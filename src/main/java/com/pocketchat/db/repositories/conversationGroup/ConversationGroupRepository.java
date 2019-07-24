package com.pocketchat.db.repositories.conversationGroup;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConversationGroupRepository extends MongoRepository<ConversationGroup, String> {
}
