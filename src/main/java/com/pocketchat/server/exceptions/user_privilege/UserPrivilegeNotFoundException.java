package com.pocketchat.server.exceptions.user_privilege;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserPrivilegeNotFoundException extends RuntimeException {
    public UserPrivilegeNotFoundException(String message) {
        super(message);
    }
}
