package com.pocketchat.utils.email;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

// https://stackoverflow.com/questions/33100298/masking-of-email-address-in-java
@Service
public class EmailUtil {
    public String maskEmail(String email) {
        if (!StringUtils.isEmpty(email)) {
            return email.replaceAll("(^[^@]{3}|(?!^)\\G)[^@]", "$1*");
        }
        return null;
    }
}
