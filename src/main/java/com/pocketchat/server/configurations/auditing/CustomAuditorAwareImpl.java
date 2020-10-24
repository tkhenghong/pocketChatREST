package com.pocketchat.server.configurations.auditing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

/**
 * https://www.javaguides.net/2018/09/spring-data-jpa-auditing-with-spring-boot2-and-mysql-example.html
 */
public class CustomAuditorAwareImpl implements AuditorAware<String> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Optional<String> getCurrentAuditor() {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        AnonymousAuthenticationToken anonymousAuthenticationToken = null;
        try {
            usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        } catch (ClassCastException classCastException) {
            logger.warn("Coming from unauthenticated APIs.");
            // Means if there's a REST call but the call is from unauthenticated APIs(such as UserAuthentication)
            anonymousAuthenticationToken = (AnonymousAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            return Optional.of(anonymousAuthenticationToken.getName());
        }

        if(ObjectUtils.isEmpty(usernamePasswordAuthenticationToken)) {
            String defaultSystemUserName = "PocketChatSystem";
            logger.info("No authenticated user for auditing for now, using {} for now.", defaultSystemUserName);
            return Optional.of(defaultSystemUserName);
        } else {
            logger.info("Authenticated User for auditing: {} detected.", usernamePasswordAuthenticationToken.getName());
            return Optional.of(usernamePasswordAuthenticationToken.getName());
        }
    }
}
