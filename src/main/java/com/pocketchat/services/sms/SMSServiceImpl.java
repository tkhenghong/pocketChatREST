package com.pocketchat.services.sms;

import com.pocketchat.models.sms.SendSMSRequest;
import com.pocketchat.models.sms.SendSMSResponse;
import org.springframework.stereotype.Service;

@Service
public class SMSServiceImpl implements SMSService {
    @Override
    public SendSMSResponse sendSMS(SendSMSRequest sendSmsRequest) {
        return null;
    }
}
