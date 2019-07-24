package com.pocketchat.db.repositories.userContact;

import com.pocketchat.db.models.user_contact.UserContact;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserContactRepository extends MongoRepository<UserContact, String> {
    List<UserContact> findByUserId(String userId);
}
