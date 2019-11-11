package com.pocketchat.db.repoServices.user;

import com.pocketchat.db.repositories.user.UserRepository;
import com.pocketchat.db.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRepoService {
    private UserRepository userRepository;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepoService(UserRepository userRepository, MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }
    public boolean existById(String userId) {
        return userRepository.existsById(userId);
    }

    public List<User> findByIdsIn(List<String> userIds) {
        Criteria criteria = new Criteria();
        criteria = criteria.andOperator(
                Criteria.where("id").in(userIds)
        );

        Query query = Query.query(criteria);
        List<User> userContactList = mongoTemplate.find(query, User.class);
        return userContactList;
    }

    public User save(User conversationGroup) {
        return userRepository.save(conversationGroup);
    }

    public void delete(User conversationGroup) {
        userRepository.delete(conversationGroup);
    }

    public Optional<User> findByGoogleAccountId(String googleAccountId) {
        return userRepository.findFirstByGoogleAccountId(googleAccountId);
    }

    public Optional<User> findByMobileNo(String googleAccountId) {
        return userRepository.findFirstByMobileNo(googleAccountId);
    }
}
