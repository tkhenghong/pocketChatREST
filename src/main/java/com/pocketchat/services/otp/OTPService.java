package com.pocketchat.utils.otp;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.pocketchat.models.otp.GenerateOTPRequest;
import com.pocketchat.models.otp.OTP;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPNumberGenerator {

    @Value("${server.otp.maximum.alive.minutes}")
    private int maximumOTPAliveMinutes;

    private final LoadingCache<String, OTP> otpCache;

    public OTPNumberGenerator() {
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

        OTP otp = OTP.builder()
                .userId(generateOTPRequest.getUserId())
                .otp(otpNumber)
                .otpExpirationDateTime(new DateTime())
                .length(generateOTPRequest.getOtpLength())
                .build();

        otpCache.put(generateOTPRequest.getUserId(), otp);

        return otp;
    }

    Integer generatePowerNumber(Integer number, Integer baseNumber, Integer exponentNumber) {
        return (int) (number * Math.pow(baseNumber, exponentNumber));
    }
}
