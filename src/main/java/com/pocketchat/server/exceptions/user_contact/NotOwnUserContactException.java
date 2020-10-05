package com.pocketchat.server.exceptions.user_contact;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Happens when the UserContact that you're editing is not your own UserContact.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotOwnUserContactException extends RuntimeException {
    public NotOwnUserContactException(String message) {
        super(message);
    }
}
