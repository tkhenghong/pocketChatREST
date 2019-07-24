package com.pocketchat.dbRepositories.message;

import com.pocketchat.models.message.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findAllByConversationId(String conversationId);
}
