package com.pocketchat.aspects.services.user_authentication;

import com.pocketchat.models.controllers.request.user_authentication.RegisterUsingMobileNumberRequest;
import com.pocketchat.models.controllers.response.user_authentication.PreVerifyMobileNumberOTPResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;


/**
 * Using Aspect for logging an activity, or perform other stuffs.
 * https://dzone.com/articles/implementing-aop-with-spring-boot-and-aspectj
 */
@Aspect
@Configuration
public class UserAuthenticationServiceAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserAuthenticationServiceAspect() {

    }

    @Around("execution(* com.pocketchat.services.user_authentication.UserAuthenticationService.registerMobileNumber(..))")
    public Object aroundRegisterMobileNumber(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("aroundRegisterMobileNumber()");
        long startTime = System.currentTimeMillis();
        String errorMessage = "";

        List<Object> args = Arrays.asList(pjp.getArgs());

        RegisterUsingMobileNumberRequest registerUsingMobileNumberRequest = (RegisterUsingMobileNumberRequest) args.get(0);

        logger.info("registerUsingMobileNumberRequest: {}", registerUsingMobileNumberRequest.toString());

        Object result = pjp.proceed();

        PreVerifyMobileNumberOTPResponse preVerifyMobileNumberOTPResponse = (PreVerifyMobileNumberOTPResponse) result;

        logger.info("preVerifyMobileNumberOTPResponse: {}", preVerifyMobileNumberOTPResponse);

        return result;
    }
}
