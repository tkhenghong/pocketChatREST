package com.pocketchat.services.sms;

import com.pocketchat.models.email.SendEmailRequest;
import com.pocketchat.models.sms.SendSMSRequest;
import com.pocketchat.models.sms.SendSMSResponse;
import com.pocketchat.services.email.EmailService;
import com.pocketchat.utils.date_time_conversion.DateTimeConversionUtil;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class SMSServiceImpl implements SMSService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${server.sms.twilio.account.sid}")
    private String accountSid;

    @Value("${server.sms.twilio.auth.token}")
    private String authToken;

    @Value("${server.sms.twilio.phone.number}")
    private String fromNumber;

    @Value("${email.send.sms.to.email}")
    private boolean allowSendSMStoEmail;

    @Value("${email.send.sms.to.email.address}")
    private String emailAddressForSendingSMSContent;

    @Value("${server.sms.twilio.verified.phone.number}")
    private String verifiedPhoneNumber;

    private final EmailService emailService;

    private final DateTimeConversionUtil dateTimeConversionUtil;

    @Autowired
    public SMSServiceImpl(EmailService emailService,
                          DateTimeConversionUtil dateTimeConversionUtil) {
        this.emailService = emailService;
        this.dateTimeConversionUtil = dateTimeConversionUtil;
    }

    @Override
    public SendSMSResponse sendSMS(SendSMSRequest sendSMSRequest) {
        if (allowSendSMStoEmail) {
            emailService.sendEmail(SendEmailRequest.builder()
                    .emailSubject("PocketChat SMS")
                    .emailContent(sendSMSRequest.getMessage())
                    .receiverList(Collections.singletonList(emailAddressForSendingSMSContent))
                    .build());
        }

        if (StringUtils.hasText(verifiedPhoneNumber) && !sendSMSRequest.getMobileNumber().equals(verifiedPhoneNumber)) {
            logger.info("Not sending unverified phone number from Twilio: {}", sendSMSRequest.getMobileNumber());
            return createSendSMSResponse(sendSMSRequest, null);
        }

        Twilio.init(accountSid, authToken);
        Message message = Message.creator(new PhoneNumber(sendSMSRequest.getMobileNumber()),
                new PhoneNumber(fromNumber),
                sendSMSRequest.getMessage())
                .create();

        return createSendSMSResponse(sendSMSRequest, message);
    }

    private SendSMSResponse createSendSMSResponse(SendSMSRequest sendSMSRequest, Message message) {
        SendSMSResponse sendSMSResponse;
        if (ObjectUtils.isEmpty(message)) {
            sendSMSResponse = SendSMSResponse.builder()
                    .mobileNumber(sendSMSRequest.getMobileNumber())
                    .message(sendSMSRequest.getMessage())
                    .build();
        } else {
            sendSMSResponse = SendSMSResponse.builder()
                    .mobileNumber(sendSMSRequest.getMobileNumber())
                    .message(sendSMSRequest.getMessage())
                    .errorCode(ObjectUtils.isEmpty(message.getErrorCode()) ? "" : message.getErrorCode().toString())
                    .errorMessage(message.getErrorMessage())
                    .dateCreated(convertLocalDateTime(message.getDateCreated().toLocalDateTime()))
                    .dateSent(convertLocalDateTime(message.getDateSent().toLocalDateTime()))
                    .dateUpdated(convertLocalDateTime(message.getDateUpdated().toLocalDateTime()))
                    .accountSid(message.getAccountSid())
                    .sid(message.getSid())
                    .apiVersion(message.getApiVersion())
                    .body(message.getBody())
                    .direction(message.getDirection().toString())
                    .fromEndPoint(message.getFrom().getEndpoint())
                    .to(message.getTo())
                    .numMedia(message.getNumMedia())
                    .numSegments(message.getNumSegments())
                    .price(message.getPrice())
                    .priceCurrency(message.getPriceUnit())
                    .uri(message.getUri())
                    .subResourceUris(message.getSubresourceUris())
                    .build();
        }

        logger.info("sendSMSResponse: {}", sendSMSResponse);

        return sendSMSResponse;
    }

    private LocalDateTime convertLocalDateTime(org.joda.time.LocalDateTime jodaLocalDateTime) {
        return dateTimeConversionUtil.convertJodaToJavaTimeLocalDateTime(jodaLocalDateTime);
    }

    @Override
    public void receiveSMS(MultiValueMap<String, String> map) {

    }
}
