package com.pocketchat.server.configurations.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfigurations {

    @Value("${email.sender.host}")
    private String emailHost;

    @Value("${email.host.port}")
    private Integer emailHostPort;

    @Value("${email.username}")
    private String emailUsername;

    @Value("${email.password}")
    private String emailPassword;

    @Value("${email.transport.protocol}")
    private String emailTransportProtocol;

    @Value("${email.smtp.auth}")
    private boolean emailSmtpAuth;

    @Value("${email.smtp.starttls.enable}")
    private boolean enablSmtpStartttls;

    @Value("${email.debug}")
    private boolean emailDebug;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailHost);
        mailSender.setPort(emailHostPort);

        mailSender.setUsername(emailUsername);
        mailSender.setPassword(emailPassword);

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", emailTransportProtocol);
        properties.put("mail.smtp.auth", emailSmtpAuth);
        properties.put("mail.smtp.starttls.enable", enablSmtpStartttls);
        properties.put("mail.debug", emailDebug);

        System.out.println("Running up Email Configuration.");
        return mailSender;
    }
}
