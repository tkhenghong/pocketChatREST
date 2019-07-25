package com.pocketchat.db.repositories.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MultimediaRepository extends MongoRepository<Multimedia, String> {
    List<Multimedia> findAllByConversationId(List<String> conversationId);
    List<Multimedia> findAllByConversationId(String conversationId);
}
