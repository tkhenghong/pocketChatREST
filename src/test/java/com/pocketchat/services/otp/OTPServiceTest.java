package com.pocketchat.services.otp;

import com.pocketchat.models.otp.OTP;
import com.pocketchat.models.otp.VerifyOTPNumberResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class OTPServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Value("${server.otp.maximumAliveMinutes}")
//    private int maximumOTPAliveMinutes;
//
//    @Value("${server.otp.maximumRetryAttempt}")
//    private int maximumOTPRetryAttempt;

    @InjectMocks
    OTPService otpService;

//    @BeforeTestMethod
//    public void setUp() {
//        otpService = spy(new OTPService(maximumOTPAliveMinutes, maximumOTPRetryAttempt));
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    @Disabled
    public void testGenerateAndGetOTPFromService() {
        OTP otp = generateOTP();

        otpService.saveOTPNumber(otp);

        VerifyOTPNumberResponse verifyOTPNumberResponse = otpService.verifyOTPNumber(otp.getUserId(), otp.getOtp().toString(), otp.getKeyword());

        assertThat(verifyOTPNumberResponse).isNotNull();
        assertThat(verifyOTPNumberResponse.getLimitRemaining()).isZero();
        assertThat(verifyOTPNumberResponse.isCorrect()).isTrue();
        assertThat(verifyOTPNumberResponse.isHasError()).isFalse();
    }

    OTP generateOTP() {
        Random random = new Random();
        OTP otp = OTP.builder()
                .length(6)
                .otp(random.nextInt((int) Math.pow(10, 6)))
                .verifyAttempt(0)
                .keyword(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .otpExpirationDateTime(LocalDateTime.now())
                .build();


//                otpService.generateOtpNumber(GenerateOTPRequest.builder()
//                .userId(UUID.randomUUID().toString())
//                .otpAliveMinutes(5)
//                .otpLength(6)
//                .build());

        logger.info("otp.toString(): {}", otp.toString());

        return otp;
    }


}
