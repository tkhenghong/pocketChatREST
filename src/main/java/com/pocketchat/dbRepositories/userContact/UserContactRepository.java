package com.pocketchat.dbRepositories.userContact;

import com.pocketchat.models.user_contact.UserContact;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserContactRepository extends MongoRepository<UserContact, String> {
    List<UserContact> findByUserId(String userId);
}
