package com.pocketchat.services.email;

import com.pocketchat.models.email.SendEmailRequest;
import com.pocketchat.models.email.SendEmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public SendEmailResponse sendEmail(SendEmailRequest sendEmailRequest) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        sendEmailRequest.getReceiverList().forEach(receiverEmailAddress -> {
            simpleMailMessage.setTo(receiverEmailAddress);
            simpleMailMessage.setSubject(sendEmailRequest.getEmailSubject());
            simpleMailMessage.setText(sendEmailRequest.getEmailContent());
            javaMailSender.send(simpleMailMessage);
        });

        return null; // TODO: Add response to indicate success.
    }
}
