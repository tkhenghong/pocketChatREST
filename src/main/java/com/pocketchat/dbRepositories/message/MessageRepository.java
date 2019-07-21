package com.pocketchat.dbRepositories.message;

import com.pocketchat.models.message.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
