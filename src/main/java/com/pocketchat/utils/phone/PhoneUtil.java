package com.pocketchat.utils.phone;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

// https://stackoverflow.com/questions/47409339/masking-phone-number-java
@Service
public class PhoneUtil {
    public String maskPhoneNumber(String phoneNo) {
        if (!StringUtils.isEmpty(phoneNo)) {
            return phoneNo.replaceAll(".(?=.{4})", "*");
        }
        return null;
    }
}
