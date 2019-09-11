package com.pocketchat.controllers.models.userContact;

import com.pocketchat.controllers.response.userContact.UserContactResponse;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.services.models.userContact.UserContactService;
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
    public UserContactResponse getUserContact(@PathVariable String userContactId) {
        return userContactResponseMapper(userContactService.getUserContact(userContactId));
    }

    @GetMapping("/mobileNo/{mobileNo}")
    public UserContactResponse getUserContactByMobileNo(@PathVariable String mobileNo) {
        return userContactResponseMapper(userContactService.getUserContactByMobileNo(mobileNo));
    }

    @GetMapping("/user/{userId}")
    public List<UserContactResponse> getUserContactsByUserId(@PathVariable String userId) {
        List<UserContact> userContactList = userContactService.getUserContactsByUserId(userId);
        return userContactList.stream().map(this::userContactResponseMapper).collect(Collectors.toList());
    }

    private UserContactResponse userContactResponseMapper(UserContact userContact) {
        return UserContactResponse.builder()
                .id(userContact.getId())
                .displayName(userContact.getDisplayName())
                .realName(userContact.getRealName())
                .block(userContact.isBlock())
                .mobileNo(userContact.getMobileNo())
                .multimediaId(userContact.getMultimediaId())
                .userIds(userContact.getUserIds())
                .lastSeenDate(userContact.getLastSeenDate().getMillis())
                .build();
    }
}
