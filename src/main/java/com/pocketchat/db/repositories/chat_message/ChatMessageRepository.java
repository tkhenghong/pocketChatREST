package com.pocketchat.db.repositories.chat_message;

import com.pocketchat.db.models.chat_message.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findAllByConversationId(String conversationId);
}
