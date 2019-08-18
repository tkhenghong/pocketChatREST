package com.pocketchat.services.userContact;

import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repoServices.userContact.UserContactRepoService;
import com.pocketchat.server.exceptions.userContact.UserContactNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserContactServiceImpl implements UserContactService {
    private final UserContactRepoService userContactRepoService;

    @Autowired
    public UserContactServiceImpl(UserContactRepoService userContactRepoService) {
        this.userContactRepoService = userContactRepoService;
    }

    @Override
    public UserContact addUserContact(UserContact userContact) {
        return userContactRepoService.save(userContact);
    }

    @Override
    public void editUserContact(UserContact userContact) {
        Optional<UserContact> userContactOptional = userContactRepoService.findById(userContact.getId());
        validateUserContactNotFound(userContactOptional, userContact.getId());
        userContactRepoService.save(userContact);
    }

    @Override
    public void deleteUserContact(String userContactId) {
        Optional<UserContact> userContactOptional = userContactRepoService.findById(userContactId);
        validateUserContactNotFound(userContactOptional, userContactId);
        userContactRepoService.delete(userContactOptional.get());
    }

    @Override
    public UserContact getUserContact(String userContactId) {
        Optional<UserContact> userContactOptional = userContactRepoService.findById(userContactId);
        validateUserContactNotFound(userContactOptional, userContactId);
        return userContactOptional.get();
    }

    private void validateUserContactNotFound(Optional<UserContact> userContactOptional, String userContactId) {
        if (!userContactOptional.isPresent()) {
            throw new UserContactNotFoundException("userContactId-" + userContactId);
        }
    }
}
