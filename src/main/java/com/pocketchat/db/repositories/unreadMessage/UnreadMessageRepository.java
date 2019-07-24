package com.pocketchat.db.repositories.unreadMessage;

import com.pocketchat.db.models.unread_message.UnreadMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UnreadMessageRepository extends MongoRepository<UnreadMessage, String> {
}
