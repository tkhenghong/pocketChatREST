package com.pocketchat.db.repositories.unreadMessage;

import com.pocketchat.db.models.unread_message.UnreadMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UnreadMessageRepository extends MongoRepository<UnreadMessage, String> {
    List<UnreadMessage> findAllByUserId(String userId);

    Optional<UnreadMessage> findByConversationId(String conversationGroupId);
}
