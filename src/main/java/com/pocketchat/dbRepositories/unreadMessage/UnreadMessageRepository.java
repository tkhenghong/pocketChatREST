package com.pocketchat.dbRepositories.unreadMessage;

import com.pocketchat.models.unread_message.UnreadMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UnreadMessageRepository extends MongoRepository<UnreadMessage, String> {
}
