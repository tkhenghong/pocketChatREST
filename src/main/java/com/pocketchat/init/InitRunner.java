package com.pocketchat.init;

import com.pocketchat.models.email.SendEmailRequest;
import com.pocketchat.models.sms.SendSMSRequest;
import com.pocketchat.services.email.EmailService;
import com.pocketchat.services.sms.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// This class is used to run necessary things every time when this Spring Boot application starts.
// Tasks that the developer want to run every time when this project runs successfully.
// 1. Send SMS
// 2. Send Email
@Component
public class InitRunner implements CommandLineRunner {

    private final SMSService smsService;

    private final EmailService emailService;

    @Value("${server.startup.send.email.enabled}")
    private boolean startupSendEmailEnabled;

    @Value("${server.startup.send.sms.enabled}")
    private boolean startupSendSmsEnabled;

    @Autowired
    InitRunner(SMSService smsService, EmailService emailService) {
        this.smsService = smsService;
        this.emailService = emailService;
    }

    @Override
    public void run(String... args) throws Exception {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String content = "PocketChat REST API Spring Boot application started successfully at " + timestamp.toString() + ".";
        if (startupSendSmsEnabled) {
            this.smsService.sendSMS(SendSMSRequest.builder().mobileNumber("+60182262663").message(content).build());
        }

        List<String> receiverEmailAddresses = new ArrayList<>();
        receiverEmailAddresses.add("tkhenghong@gmail.com");

        if (startupSendEmailEnabled) {
            this.emailService.sendEmail(SendEmailRequest.builder()
                    .receiverList(receiverEmailAddresses)
                    .emailSubject("PocketChat REST Started Successfully")
                    .emailContent(content).build()
            );
        }
    }
}
