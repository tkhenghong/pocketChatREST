package com.pocketchat.dbRepositories.conversationGroup;

import com.pocketchat.models.conversation_group.ConversationGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConversationGroupRepository extends MongoRepository<ConversationGroup, String> {
}
