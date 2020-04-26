package com.pocketchat.services.email;

import com.pocketchat.models.email.SendEmailRequest;
import com.pocketchat.models.email.SendEmailResponse;

public interface EmailService {
    SendEmailResponse sendEmail(SendEmailRequest sendEmailRequest);
}
