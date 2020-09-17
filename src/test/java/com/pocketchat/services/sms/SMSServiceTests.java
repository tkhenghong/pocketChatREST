package com.pocketchat.services.sms;


import com.pocketchat.services.email.EmailService;
import com.pocketchat.utils.date_time_conversion.DateTimeConversionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SMSServiceTests {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${server.sms.twilio.account.sid}")
    private String accountSid = "ACb8bfda44c37c2c6e476ba1fda795c668";

    @Value("${server.sms.twilio.auth.token}")
    private String authToken = "78ac8dc2cd60ddd5394a3580c6386c04";

    @Value("${server.sms.twilio.phone.number}")
    private String fromNumber = "+14042058771";

    @Value("${email.send.sms.to.email}")
    private boolean allowSendSMStoEmail = true;

    @Value("${email.send.sms.to.email.address}")
    private String emailAddressForSendingSMSContent = "tkhenghong@yahoo.com";

    @Value("${server.sms.twilio.verified.phone.number}")
    private String verifiedPhoneNumber = "+60182262663";

    SMSService smsService;

    @Mock
    EmailService emailService;

    @Mock
    DateTimeConversionUtil dateTimeConversionUtil;

    @BeforeEach
    void setup() {
        smsService = new SMSServiceImpl(
                accountSid,
                authToken,
                fromNumber,
                allowSendSMStoEmail,
                emailAddressForSendingSMSContent,
                verifiedPhoneNumber,
                emailService,
                dateTimeConversionUtil
        );
    }

    // Send SMS but no mobile number
    // Send SMS but no SMS content
    // Send SMS and allowSendSMStoEmail is false
    // Send SMS and allowSendSMStoEmail is true
    // Send unverified SMS
    // Send verified SMS

}
