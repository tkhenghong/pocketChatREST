package com.pocketchat.db.repositories.chat_message;

import com.pocketchat.db.models.chat_message.ChatMessage;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

@JaversSpringDataAuditable
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    Page<ChatMessage> findAllByConversationId(String conversationId, Pageable pageable);
}
