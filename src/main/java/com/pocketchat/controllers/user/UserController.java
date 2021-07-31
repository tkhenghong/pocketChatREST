package com.pocketchat.controllers.user;

import com.pocketchat.models.controllers.request.user.UpdateUserRequest;
import com.pocketchat.models.controllers.response.user.UserResponse;
import com.pocketchat.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("")
    public UserResponse editUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.userResponseMapper(userService.editUser(updateUserRequest));
    }

    @GetMapping("")
    public UserResponse getOwnUser() {
        return userService.userResponseMapper(userService.getOwnUser());
    }
}
