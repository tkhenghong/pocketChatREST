package com.pocketchat.db.repoServices.userContact;

import com.pocketchat.db.repositories.userContact.UserContactRepository;
import com.pocketchat.db.models.user_contact.UserContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserContactRepoService {
    private final UserContactRepository userContactRepository;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserContactRepoService(UserContactRepository userContactRepository, MongoTemplate mongoTemplate) {
        this.userContactRepository = userContactRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<UserContact> findById(String userContactId) {
        return userContactRepository.findById(userContactId);
    }

    public List<UserContact> findByUserIdIn(List<String> userIds) {
        System.out.println("UserContactRepoService.java findByUserIdIn()");
        System.out.println("UserContactRepoService.java userIds.size(): " + userIds.size());

        Criteria criteria = new Criteria();
        criteria = criteria.andOperator(
                Criteria.where("id").in(userIds)
        );
        Query query = Query.query(criteria);
        List<UserContact> userContactList = mongoTemplate.find(query, UserContact.class);
        System.out.println("userContactList.size(): " + userContactList.size());

        return userContactList;
    }

    public Optional<UserContact> findByMobileNo(String mobileNo) {
        return userContactRepository.findByMobileNo(mobileNo);
    }

    public List<UserContact> findByUserIds(String userId) {
        return userContactRepository.findByUserIds(userId);
    }

    public UserContact save(UserContact conversationGroup) {
        return userContactRepository.save(conversationGroup);
    }

    public void delete(UserContact conversationGroup) {
        userContactRepository.delete(conversationGroup);
    }
}
