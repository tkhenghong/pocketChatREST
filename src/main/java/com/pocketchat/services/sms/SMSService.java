package com.pocketchat.services.sms;

import com.pocketchat.models.sms.SendSMSRequest;
import com.pocketchat.models.sms.SendSMSResponse;

public interface SMSService {
    SendSMSResponse sendSMS(SendSMSRequest sendSmsRequest);
}
