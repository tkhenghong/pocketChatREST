package com.pocketchat.controllers.userContact;

import com.pocketchat.dbRepoServices.userContact.UserContactRepoService;
import com.pocketchat.models.user_contact.UserContact;
import com.pocketchat.server.exceptions.userContact.UserContactNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/userContact")
public class UserContactController {

    private final UserContactRepoService userContactRepoService;

    @Autowired
    public UserContactController(UserContactRepoService userContactRepoService) {
        this.userContactRepoService = userContactRepoService;
    }

    @PostMapping("")
    public ResponseEntity<Object> addUserContact(@Valid @RequestBody UserContact userContact) {
        UserContact savedUserContact = userContactRepoService.save(userContact);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUserContact.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    // Add a bunch of UserContacts
    // Don't have to send UserContacts one by one from front end
//    public ResponseEntity<List<Object>> addUserContactList() {
//
//    }

    @PutMapping("")
    public void editUserContact(@Valid @RequestBody UserContact userContact) {
        Optional<UserContact> userContactOptional = userContactRepoService.findById(userContact.getId());

        if (!userContactOptional.isPresent()) {
            throw new UserContactNotFoundException("userIdContactId:-" + userContact.getId());
        }

        userContactRepoService.save(userContact);
    }

    @DeleteMapping("/{userContactId}")
    public void deleteUserContact(@PathVariable String userContactId) {
        Optional<UserContact> userContactOptional = userContactRepoService.findById(userContactId);
        if (!userContactOptional.isPresent()) {
            throw new UserContactNotFoundException("userContactId-" + userContactId);
        }

        userContactRepoService.delete(userContactOptional.get());
    }

    @GetMapping("/{userContactId}")
    public UserContact getUserContact(@PathVariable String userContactId) {
        Optional<UserContact> userContactOptional = userContactRepoService.findById(userContactId);

        if (!userContactOptional.isPresent()) {
            throw new UserContactNotFoundException("userContactId-" + userContactId);
        }

        return userContactOptional.get();
    }
}
