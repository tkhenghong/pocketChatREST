package com.pocketchat.controllers.userContact;

import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.services.userContact.UserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/userContact")
public class UserContactController {
    private final UserContactService userContactService;

    @Autowired
    public UserContactController(UserContactService userContactService) {
        this.userContactService = userContactService;
    }

    @PostMapping("")
    public ResponseEntity<Object> addUserContact(@Valid @RequestBody UserContact userContact) {
        UserContact savedUserContact = userContactService.addUserContact(userContact);

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
        userContactService.editUserContact(userContact);
    }

    @DeleteMapping("/{userContactId}")
    public void deleteUserContact(@PathVariable String userContactId) {
        userContactService.deleteUserContact(userContactId);
    }

    @GetMapping("/{userContactId}")
    public UserContact getUserContact(@PathVariable String userContactId) {
        return userContactService.getUserContact(userContactId);
    }
}
