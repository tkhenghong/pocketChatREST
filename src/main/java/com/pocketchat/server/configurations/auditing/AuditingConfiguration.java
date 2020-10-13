package com.pocketchat.server.configurations.auditing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * Using the architecture of below for auditorAwareRef:
 * https://www.baeldung.com/database-auditing-jpa
 * A bit reference from here: https://codeboje.de/spring-data-auditing/
 */
@Configuration
@EnableMongoAuditing(auditorAwareRef = "auditorAware")
public class AuditingConfiguration {

    @Bean
    AuditorAware<String> auditorAware() {
        return new CustomAuditorAwareImpl();
    }
}
