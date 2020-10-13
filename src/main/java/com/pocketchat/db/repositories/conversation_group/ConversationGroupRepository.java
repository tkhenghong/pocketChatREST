package com.pocketchat.db.repositories.conversation_group;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConversationGroupRepository extends MongoRepository<ConversationGroup, String> {
    List<ConversationGroup> findAllByMemberIds(String userContactId);
    Page<ConversationGroup> findAllByMemberIds(String userContactId, Pageable pageable);
    List<ConversationGroup> findAllByMemberIds(List<String> memberIds);
}
