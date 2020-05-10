package com.pocketchat.server.configurations.init;

import com.pocketchat.models.email.SendEmailRequest;
import com.pocketchat.models.sms.SendSMSRequest;
import com.pocketchat.services.email.EmailService;
import com.pocketchat.services.sms.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// This class is used for running necessary things every time when this Spring Boot application starts.
// Tasks that the developer want to run every time when this project runs successfully.
// 1. Send SMS
// 2. Send Email
@Component
public class InitRunner implements CommandLineRunner {

    private final SMSService smsService;

    private final EmailService emailService;

    @Autowired
    InitRunner(SMSService smsService, EmailService emailService) {
        this.smsService = smsService;
        this.emailService = emailService;
    }

    @Override
    public void run(String... args) throws Exception {
        String content = "PocketChat REST API Spring Boot application started successfully.";
        this.smsService.sendSMS(SendSMSRequest.builder().mobileNumber("+60182262663").message(content).build());

        List<String> receiverEmailAddresses = new ArrayList<>();
        receiverEmailAddresses.add("tkhenghong@gmail.com");

        this.emailService.sendEmail(SendEmailRequest.builder()
                .receiverList(receiverEmailAddresses)
                .emailSubject("PocketChat REST Started Successfully")
                .emailContent(content).build()
        );
    }
}
