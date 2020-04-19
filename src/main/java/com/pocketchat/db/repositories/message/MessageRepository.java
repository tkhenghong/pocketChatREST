package com.pocketchat.db.repositories.message;

import com.pocketchat.db.models.chat_message.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findAllByConversationId(String conversationId);
}
