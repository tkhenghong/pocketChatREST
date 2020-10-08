package com.pocketchat.utils.server_shutdown;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * This Util is used to shut down the Spring Boot application programmatically.
 * It could be used to prevent future error.
 * https://stackoverflow.com/questions/22944144/programmatically-shut-down-spring-boot-application
 */
@Component
public class ServerShutdownUtil {

    private final ApplicationContext applicationContext;

    @Autowired
    ServerShutdownUtil(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void initiateShutdown(int returnCode) {
        SpringApplication.exit(applicationContext, () -> returnCode);
    }
}
