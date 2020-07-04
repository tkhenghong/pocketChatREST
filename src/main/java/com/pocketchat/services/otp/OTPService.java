package com.pocketchat.services.otp;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.pocketchat.models.otp.GenerateOTPRequest;
import com.pocketchat.models.otp.OTP;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {

    @Value("${server.otp.maximum.alive.minutes}")
    private int maximumOTPAliveMinutes;

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
                .length(generateOTPRequest.getOtpLength())
                .build();
    }

    public void saveOTPNumber(OTP otp) {
        otpCache.put(otp.getUserId(), otp);
    }

    Integer generatePowerNumber(Integer number, Integer baseNumber, Integer exponentNumber) {
        return (int) (number * Math.pow(baseNumber, exponentNumber));
    }

    // https://www.baeldung.com/java-random-string#java8-alphabetic
    public String generateRandomSecureKeyword() {
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
