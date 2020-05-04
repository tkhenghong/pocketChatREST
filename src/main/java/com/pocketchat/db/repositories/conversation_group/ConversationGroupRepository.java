package com.pocketchat.db.repositories.conversation_group;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConversationGroupRepository extends MongoRepository<ConversationGroup, String> {
    List<ConversationGroup> findAllByMemberIds(String userContactId);

    List<ConversationGroup> findAllByMemberIds(List<String> memberIds);
}
