package com.pocketchat.services.rabbitmq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RabbitMQMessageCustomRetryTemplate {

    @Value("${spring.rabbitmq.listener.simple.retry.enabled}")
    boolean simpleRetryTemplateEnabled;

    @Value("${spring.rabbitmq.listener.simple.retry.initial-interval}")
    int retryInitialInterval; // In milliseconds

    @Value("${spring.rabbitmq.listener.simple.retry.max-attempts}")
    int retryMaxAttempts;

    @Value("${spring.rabbitmq.listener.simple.retry.max-interval}")
    int retryMaxInterval; // In milliseconds

    @Value("${spring.rabbitmq.listener.simple.retry.max-interval}")
    int retryMultiplier;

    @Bean(name = "RabbitMQRetryTemplate")
    public RetryTemplate rabbitMQRetryTemplate() {
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(4);
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(3000);
        RetryTemplate template = new RetryTemplate();
        template.setRetryPolicy(retryPolicy);
        template.setBackOffPolicy(backOffPolicy);
        return template;
    }

}
