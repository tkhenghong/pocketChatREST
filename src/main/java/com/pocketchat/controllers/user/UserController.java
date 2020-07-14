package com.pocketchat.controllers.user;

import com.pocketchat.models.controllers.request.user.CreateUserRequest;
import com.pocketchat.models.controllers.request.user.UpdateUserRequest;
import com.pocketchat.models.controllers.response.user.UserResponse;
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
    public ResponseEntity<Object> addUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        UserResponse savedUser = userService.userResponseMapper(userService.addUser(createUserRequest));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("")
    public UserResponse editUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.userResponseMapper(userService.editUser(updateUserRequest));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable String userId) {
        return userService.userResponseMapper(userService.getUser(userId));
    }

    @GetMapping("/googleAccountId/{googleAccountId}")
    public UserResponse getUserByGoogleAccountId(@PathVariable String googleAccountId) {
        return userService.userResponseMapper(userService.getUserByGoogleAccountId(googleAccountId));
    }

    @GetMapping("/mobileNo/{mobileNo}")
    public UserResponse getUserByMobileNo(@PathVariable String mobileNo) {
        return userService.userResponseMapper(userService.getUserByMobileNo(mobileNo));
    }
}
