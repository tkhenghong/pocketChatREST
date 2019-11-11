package com.pocketchat.db.repoServices.userContact;

import com.pocketchat.db.repositories.userContact.UserContactRepository;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.server.exceptions.userContact.UserContactNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Criteria criteria = new Criteria();
        criteria = criteria.andOperator(
                Criteria.where("id").in(userIds)
        );
        Query query = Query.query(criteria);
        List<UserContact> userContactList = mongoTemplate.find(query, UserContact.class);

        return userContactList;
    }

    private boolean isMalaysianNumber(String mobileNo) {
        Pattern pattern = Pattern.compile("^(\\+?6?01)[0-46-9]-*[0-9]{7,8}$");

        Matcher matcher = pattern.matcher(mobileNo);
        boolean isMalaysianNumber = matcher.find() && matcher.group().equals(mobileNo);
        return isMalaysianNumber;
    }

    public UserContact findByMobileNo(String mobileNo) {
        boolean malaysianNumberWithPlus = isMalaysianNumber(mobileNo) && mobileNo.contains("+");

        if (malaysianNumberWithPlus) {
            // For Malaysian number only
            mobileNo = mobileNo.substring(2);
        }
        String filteredMobileNo = mobileNo.replaceAll("[-+.^:,]","");

        Criteria criteria = new Criteria();
        criteria = criteria.andOperator(
                Criteria.where("mobileNo").regex(filteredMobileNo, "im")
        );

        Query query = Query.query(criteria).limit(1);
        UserContact userContact = mongoTemplate.findOne(query, UserContact.class);
        if (ObjectUtils.isEmpty(userContact)) {
            throw new UserContactNotFoundException("UserContact is not found. Mobile number : " + mobileNo);
        }
        return userContact;
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
