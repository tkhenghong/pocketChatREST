package com.pocketchat.utils.email;

import org.springframework.stereotype.Service;

// https://stackoverflow.com/questions/33100298/masking-of-email-address-in-java
@Service
public class EmailUtil {
    public String maskEmail(String email) {
        return email.replaceAll("(^[^@]{3}|(?!^)\\G)[^@]", "$1*");
    }
}
