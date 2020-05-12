package com.pocketchat.services.sms;

import com.pocketchat.models.sms.SendSMSRequest;
import com.pocketchat.models.sms.SendSMSResponse;
import org.springframework.util.MultiValueMap;

public interface SMSService {
    SendSMSResponse sendSMS(SendSMSRequest sendSmsRequest);

    void receiveSMS(MultiValueMap<String, String> map);
}
