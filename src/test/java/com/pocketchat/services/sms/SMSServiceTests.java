package com.pocketchat.services.sms;

import com.pocketchat.models.sms.SendSMSRequest;
import com.pocketchat.models.sms.SendSMSResponse;
import com.pocketchat.server.exceptions.sms.InvalidSendSMSRequestException;
import com.pocketchat.services.email.EmailService;
import com.pocketchat.utils.date_time_conversion.DateTimeConversionUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SMSServiceTests {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${server.sms.twilio.account.sid}")
    private final String accountSid = "ACb8bfda44c37c2c6e476ba1fda795c668";

    @Value("${server.sms.twilio.auth.token}")
    private final String authToken = "7d05382c81b39d86c8635d7f195e1093";

    @Value("${server.sms.twilio.phone.number}")
    private final String fromNumber = "+14042058771";

    @Value("${email.send.sms.to.email}")
    private boolean allowSendSMStoEmail = true;

    @Value("${email.send.sms.to.email.address}")
    private final String emailAddressForSendingSMSContent = "tkhenghong@yahoo.com";

    @Value("${server.sms.twilio.verified.phone.number}")
    private final String verifiedPhoneNumber = "+60182262663";

    @Value("${server.sms.allow.send.sms}")
    private final boolean allowSendSms = false;

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
                allowSendSms,
                emailService,
                dateTimeConversionUtil
        );
    }

    /**
     * Send SMS but no mobile number in SendSMSRequest object.
     */
    @Test
    void sendSMSWithoutMobileNumber() {
        SendSMSRequest sendSMSRequest = generateSendSMSRequestObject();
        sendSMSRequest.setMobileNumber(null);
        assertThrows(InvalidSendSMSRequestException.class, () -> {
            smsService.sendSMS(sendSMSRequest);
        });
    }

    /**
     * Send SMS but no content in SendSMSRequest object.
     */
    @Test
    @DisplayName("throws InvalidSendSMSRequestException when SMS doesn't have any content")
    void sendSMSWithoutMessageContent() {
        SendSMSRequest sendSMSRequest = generateSendSMSRequestObject();
        sendSMSRequest.setMessage(null);
        assertThrows(InvalidSendSMSRequestException.class, () -> {
            smsService.sendSMS(sendSMSRequest);
        });
    }

    /**
     * Send SMS with allowSendSMStoEmail is false.
     */
    @Test
    void sendSMSWithAllowSendSMStoEmailIsFalse() {
        allowSendSMStoEmail = false;
        setup();

        SendSMSRequest sendSMSRequest = generateSendSMSRequestObject();

        SendSMSResponse sendSMSResponse = smsService.sendSMS(sendSMSRequest);

        Mockito.verify(emailService, times(0)).sendEmail(any());

        assertNotNull(sendSMSResponse);
        assertEquals(sendSMSResponse.getMessage(), sendSMSRequest.getMessage());
        assertEquals(sendSMSResponse.getMobileNumber(), sendSMSRequest.getMobileNumber());
    }

    /**
     * Send SMS with allowSendSMStoEmail is true.
     */
    @Test
    void sendSMSWithAllowSendSMStoEmailIsTrue() {
        SendSMSRequest sendSMSRequest = generateSendSMSRequestObject();
        SendSMSResponse sendSMSResponse = smsService.sendSMS(sendSMSRequest);

        Mockito.verify(emailService).sendEmail(any());

        assertNotNull(sendSMSResponse);
        assertEquals(sendSMSResponse.getMessage(), sendSMSRequest.getMessage());
        assertEquals(sendSMSResponse.getMobileNumber(), sendSMSRequest.getMobileNumber());
    }

    /**
     * Send SMS with unverified mobile number.
     */
    @Test
    void sendSMSWithUnverifiedMobileNumber() {
        SendSMSRequest sendSMSRequest = generateSendSMSRequestObject();
        sendSMSRequest.setMobileNumber(UUID.randomUUID().toString());

        SendSMSResponse sendSMSResponse = smsService.sendSMS(sendSMSRequest);

        Mockito.verify(emailService).sendEmail(any());

        assertNotNull(sendSMSResponse);
        assertEquals(sendSMSResponse.getMessage(), sendSMSRequest.getMessage());
        assertEquals(sendSMSResponse.getMobileNumber(), sendSMSRequest.getMobileNumber());
        assertNull(sendSMSResponse.getSid());
    }

    /**
     * Send SMS with verified mobile number.
     * NOTE: Use your own Twilio account's credentials.
     */
    @Test
    @Disabled
    void sendSMSWithVerifiedMobileNumber() {
        SendSMSRequest sendSMSRequest = generateSendSMSRequestObject();
        sendSMSRequest.setMobileNumber(verifiedPhoneNumber);

        SendSMSResponse sendSMSResponse = smsService.sendSMS(sendSMSRequest);

        Mockito.verify(emailService).sendEmail(any());

        assertNotNull(sendSMSResponse);
        assertEquals(sendSMSResponse.getMessage(), sendSMSRequest.getMessage());
        assertEquals(sendSMSResponse.getMobileNumber(), sendSMSRequest.getMobileNumber());
        assertNotNull(sendSMSResponse.getSid());
    }

    private SendSMSRequest generateSendSMSRequestObject() {
        return SendSMSRequest.builder()
                .mobileNumber(UUID.randomUUID().toString())
                .message(UUID.randomUUID().toString())
                .build();
    }
}
