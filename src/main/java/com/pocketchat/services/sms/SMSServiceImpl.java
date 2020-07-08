package com.pocketchat.services.sms;

import com.pocketchat.models.sms.SendSMSRequest;
import com.pocketchat.models.sms.SendSMSResponse;
import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SMSServiceImpl implements SMSService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${server.sms.twilio.account.sid}")
    private String accountSid;

    @Value("${server.sms.twilio.auth.token}")
    private String authToken;

    @Value("${server.sms.twilio.phone.number}")
    private String fromNumber;

    @Override
    public SendSMSResponse sendSMS(SendSMSRequest sendSMSRequest) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(new PhoneNumber(sendSMSRequest.getMobileNumber()),
                new PhoneNumber(fromNumber),
                sendSMSRequest.getMessage())
                .create();
        logger.info("Here's my id: {}", message.getSid());// Unique resource ID created to manage this transaction
        return null;
    }

    @Override
    public void receiveSMS(MultiValueMap<String, String> map) {

    }
}
