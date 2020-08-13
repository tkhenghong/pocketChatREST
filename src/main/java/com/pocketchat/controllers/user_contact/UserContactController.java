package com.pocketchat.controllers.user_contact;

import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.models.controllers.request.user_contact.CreateUserContactRequest;
import com.pocketchat.models.controllers.request.user_contact.UpdateUserContactRequest;
import com.pocketchat.models.controllers.response.user_contact.UserContactResponse;
import com.pocketchat.services.user_contact.UserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/userContact")
public class UserContactController {

    private final UserContactService userContactService;

    @Autowired
    public UserContactController(UserContactService userContactService) {
        this.userContactService = userContactService;
    }

    @PostMapping("")
    public ResponseEntity<Object> addUserContact(@Valid @RequestBody CreateUserContactRequest userContact) {
        UserContactResponse savedUserContact = userContactService.addUserContact(userContact);

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
    public void editUserContact(@Valid @RequestBody UpdateUserContactRequest userContact) {
        userContactService.editUserContact(userContact);
    }

    @DeleteMapping("/{userContactId}")
    public void deleteUserContact(@PathVariable String userContactId) {
        userContactService.deleteUserContact(userContactId);
    }

    @GetMapping("/{userContactId}")
    public UserContactResponse getUserContact(@PathVariable String userContactId) {
        return userContactService.userContactResponseMapper(userContactService.getUserContact(userContactId));
    }

    @GetMapping("/mobileNo/{mobileNo}")
    public UserContactResponse getUserContactByMobileNo(@PathVariable String mobileNo) {
        return userContactService.userContactResponseMapper(userContactService.getUserContactByMobileNo(mobileNo));
    }

    @GetMapping("/")
    public UserContactResponse getOwnUserContact() {
        return userContactService.userContactResponseMapper(userContactService.getOwnUserContact());
    }

    @GetMapping("/user/{userId}")
    public List<UserContactResponse> getUserContactsByUserId(@PathVariable String userId) {
        List<UserContact> userContactList = userContactService.getUserContactsByUserId(userId);
        return userContactList.stream().map(userContactService::userContactResponseMapper).collect(Collectors.toList());
    }
}
