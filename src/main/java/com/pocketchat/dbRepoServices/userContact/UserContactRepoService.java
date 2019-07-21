package com.pocketchat.dbRepoServices.userContact;

import com.pocketchat.dbRepositories.userContact.UserContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserContactRepoService {
    @Autowired
    private UserContactRepository userContactRepository;

    public UserContactRepository getUserContactRepository() {
        return userContactRepository;
    }
}
