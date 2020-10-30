package com.pocketchat.db.repositories.user_contact;

import com.pocketchat.db.models.user_contact.UserContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserContactRepository extends MongoRepository<UserContact, String> {
    List<UserContact> findByUserIds(String userId);

    Page<UserContact> findAllByUserIds(String userId, Pageable pageable);

    Optional<UserContact> findFirstByUserId(String userId);

//    Optional<UserContact> findByMobileNo(String mobileNo);
}
