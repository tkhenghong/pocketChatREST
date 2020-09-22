package com.pocketchat.services.sms;

import com.pocketchat.models.sms.SendSMSRequest;
import com.pocketchat.models.sms.SendSMSResponse;
import com.pocketchat.server.exceptions.sms.InvalidSendSMSRequestException;
import com.pocketchat.services.email.EmailService;
import com.pocketchat.utils.date_time_conversion.DateTimeConversionUtil;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SMSServiceTests {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${server.sms.twilio.account.sid}")
    private String accountSid = "ACb8bfda44c37c2c6e476ba1fda795c668";

    @Value("${server.sms.twilio.auth.token}")
    private String authToken = "7d05382c81b39d86c8635d7f195e1093";

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

    /**
     * Send SMS but no mobile number in SendSMSRequest object.
     */
    @Test
    void sendSMSWithoutMobileNumber() {
        SendSMSRequest sendSMSRequest = generateSendSMSRequestObject();
        sendSMSRequest.setMobileNumber(null);
        try {
            smsService.sendSMS(sendSMSRequest);
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(InvalidSendSMSRequestException.class);
        }
    }

    /**
     * Send SMS but no content in SendSMSRequest object.
     */
    @Test
    void sendSMSWithoutMessageContent() {
        SendSMSRequest sendSMSRequest = generateSendSMSRequestObject();
        sendSMSRequest.setMessage(null);
        try {
            smsService.sendSMS(sendSMSRequest);
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(InvalidSendSMSRequestException.class);
        }
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
     * NOTE: If the testing account balance runs low, create another account to make this success again.
     */
    @Test
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
