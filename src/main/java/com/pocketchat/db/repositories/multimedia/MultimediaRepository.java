package com.pocketchat.db.repositories.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MultimediaRepository extends MongoRepository<Multimedia, String> {
    List<Multimedia> findAllByConversationId(List<String> conversationId);
    List<Multimedia> findAllByConversationId(String conversationId);
    Optional<Multimedia> findByUserId(String userId);
    Optional<Multimedia> findByUserContactId(String userContactId);
    Optional<Multimedia> findByConversationIdAndMessageId(String conversationGroupId, String messageId);
}
