package com.pocketchat.services.otp;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.pocketchat.models.otp.GenerateOTPRequest;
import com.pocketchat.models.otp.OTP;
import com.pocketchat.models.otp.VerifyOTPNumberResponse;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {

    @Value("${server.otp.maximum.alive.minutes}")
    private int maximumOTPAliveMinutes;

    @Value("${server.otp.maximum.retry.attempt}")
    private int maximumOTPRetryAttempt;

    // UserID with OTP object
    private final LoadingCache<String, OTP> otpCache;

    public OTPService() {
        super();
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(maximumOTPAliveMinutes, TimeUnit.MINUTES)
                .build(new CacheLoader<String, OTP>() {
                    @Override
                    public OTP load(String key) throws Exception {
                        return null;
                    }
                });
    }

    public OTP generateOtpNumber(GenerateOTPRequest generateOTPRequest) {
        Random random = new Random();
        Integer otpNumber = generatePowerNumber(1, 10, generateOTPRequest.getOtpLength()) + random.nextInt(generatePowerNumber(9, 10, generateOTPRequest.getOtpLength()));

        return OTP.builder()
                .userId(generateOTPRequest.getUserId())
                .otp(otpNumber)
                .keyword(generateRandomSecureKeyword())
                .otpExpirationDateTime(new DateTime())
                .verifyAttempt(0)
                .length(generateOTPRequest.getOtpLength())
                .build();
    }

    public void saveOTPNumber(OTP otp) {
        otpCache.put(otp.getUserId(), otp);
    }

    public VerifyOTPNumberResponse verifyOTPNumber(String userId, String otpNumber, String secureKeyword)  {
        try {
            OTP otp = otpCache.get(userId);

            if (otp.getVerifyAttempt() >= maximumOTPRetryAttempt) {
                otpCache.invalidate(otp.getUserId());
                return VerifyOTPNumberResponse.builder()
                        .correct(false)
                        .hasError(false)
                        .limitRemaining(-1)
                        .build();
            }

            boolean correctOTP = otp.getKeyword().equals(secureKeyword) && otp.getOtp().toString().equals(otpNumber);
            if (correctOTP) {
               otpCache.invalidate(otp.getUserId());
               return VerifyOTPNumberResponse.builder()
                       .correct(true)
                       .hasError(false)
                       .limitRemaining(0)
                       .build();
            } else {
             otp.setVerifyAttempt(otp.getVerifyAttempt() + 1);
             otpCache.put(otp.getUserId(), otp);
             return VerifyOTPNumberResponse.builder()
                     .correct(false)
                     .hasError(false)
                     .limitRemaining(maximumOTPRetryAttempt - otp.getVerifyAttempt())
                     .build();
            }
        } catch(ExecutionException ex) {
            return VerifyOTPNumberResponse.builder()
                    .correct(false)
                    .hasError(true)
                    .limitRemaining(0)
                    .build();
        }
    }

    private Integer generatePowerNumber(Integer number, Integer baseNumber, Integer exponentNumber) {
        return (int) (number * Math.pow(baseNumber, exponentNumber));
    }

    // https://www.baeldung.com/java-random-string#java8-alphabetic
    private String generateRandomSecureKeyword() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString.toUpperCase();
    }
}
