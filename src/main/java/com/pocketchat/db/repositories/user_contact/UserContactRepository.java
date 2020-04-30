package com.pocketchat.db.repositories.user_contact;

import com.pocketchat.db.models.user_contact.UserContact;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserContactRepository extends MongoRepository<UserContact, String> {
    List<UserContact> findByUserIds(String userId);

//    Optional<UserContact> findByMobileNo(String mobileNo);
}
