package com.pocketchat.dbRepositories.userContact;

import com.pocketchat.models.user_contact.UserContact;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserContactRepository extends MongoRepository<UserContact, String> {
}
