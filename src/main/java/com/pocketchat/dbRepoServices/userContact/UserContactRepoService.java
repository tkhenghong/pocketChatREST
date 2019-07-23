package com.pocketchat.dbRepoServices.userContact;

import com.pocketchat.dbRepositories.userContact.UserContactRepository;
import com.pocketchat.models.user_contact.UserContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserContactRepoService {
    private final UserContactRepository userContactRepository;

    @Autowired
    public UserContactRepoService(UserContactRepository userContactRepository) {
        this.userContactRepository = userContactRepository;
    }

    public UserContactRepository getUserContactRepository() {
        return userContactRepository;
    }

    public List<UserContact> findByUserId(String userId) {
        return userContactRepository.findByUserId(userId);
    }

    public UserContact save(UserContact conversationGroup) {
        return userContactRepository.save(conversationGroup);
    }

    public void delete(UserContact conversationGroup) {
        userContactRepository.delete(conversationGroup);
    }
}
