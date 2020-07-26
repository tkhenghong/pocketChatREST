package com.pocketchat.services.otp;

import com.pocketchat.models.otp.GenerateOTPRequest;
import com.pocketchat.models.otp.OTP;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class OTPServiceTest {
    @InjectMocks
    OTPService otpService;

    @Test
    public void testGenerateOTPNumber() {
        OTP otp = otpService.generateOtpNumber(GenerateOTPRequest.builder()
                .userId(UUID.randomUUID().toString())
                .otpAliveMinutes(5)
                .otpLength(6)
                .build());

        System.out.println("otp.toString(): " + otp.toString());
    }
}
