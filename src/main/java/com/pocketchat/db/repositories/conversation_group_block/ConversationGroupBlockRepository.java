package com.pocketchat.db.repositories.conversation_group_block;

import com.pocketchat.db.models.conversation_group_block.ConversationGroupBlock;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ConversationGroupBlockRepository extends MongoRepository<ConversationGroupBlock, String> {
    boolean existsByUserContactIdAndConversationGroupId(String userContactId, String conversationGroupId);
    Optional<ConversationGroupBlock> findByUserContactIdAndConversationGroupId(String userContactId, String conversationGroupId);
}
