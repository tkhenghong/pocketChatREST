package com.pocketchat.db.repoServices.userContact;

import com.pocketchat.db.repositories.userContact.UserContactRepository;
import com.pocketchat.db.models.user_contact.UserContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserContactRepoService {
    private final UserContactRepository userContactRepository;

    @Autowired
    public UserContactRepoService(UserContactRepository userContactRepository) {
        this.userContactRepository = userContactRepository;
    }

    public Optional<UserContact> findById(String userContactId) {
        return userContactRepository.findById(userContactId);
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
