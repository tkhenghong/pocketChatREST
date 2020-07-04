package com.pocketchat.utils.phone;

import org.springframework.stereotype.Service;

// https://stackoverflow.com/questions/47409339/masking-phone-number-java
@Service
public class PhoneUtil {
    public String maskPhoneNumber(String phoneNo) {
        return phoneNo.replaceAll(".(?=.{4})", "*");
    }
}
