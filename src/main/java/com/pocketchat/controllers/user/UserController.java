package com.pocketchat.controllers.user;

import com.pocketchat.db.models.user.User;
import com.pocketchat.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        User savedUser = userService.addUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("")
    public void editUser(@Valid @RequestBody User user) {
        userService.editUser(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/googleAccountId/{googleAccountId}")
    public User getUserByGoogleAccountId(@PathVariable String googleAccountId) {
        return userService.getUserByGoogleAccountId(googleAccountId);
    }

    @GetMapping("/mobileNo/{mobileNo}")
    public User getUserByMobileNo(@PathVariable String mobileNo) {
        return userService.getUserByMobileNo(mobileNo);
    }
}
