package com.pocketchat.services.email;

import com.pocketchat.models.email.SendEmailRequest;
import com.pocketchat.models.email.SendEmailResponse;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public SendEmailResponse sendEmail(SendEmailRequest sendEmailRequest) {
        return null;
    }
}
