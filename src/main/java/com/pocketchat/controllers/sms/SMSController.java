package com.pocketchat.controllers.sms;

import com.pocketchat.models.sms.SendSMSRequest;
import com.pocketchat.services.sms.SMSService;
import com.twilio.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class SMSController {

    private final SMSService smsService;

    private final SimpMessagingTemplate simpMessagingTemplate; // WebSocket

    private final String TOPIC_DESTINATION = "/topic/sms";

    @Autowired
    SMSController(SMSService smsService, SimpMessagingTemplate simpMessagingTemplate) {
        this.smsService = smsService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostMapping("/sms")
    public void sendSms(@RequestBody SendSMSRequest sendSMSRequest) {
        try {
            smsService.sendSMS(sendSMSRequest);
        } catch (ApiException e) {
            simpMessagingTemplate.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": Error sending the SMS: " + e.getMessage());
            throw e;
        }
        simpMessagingTemplate.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": SMS has been sent!: " + sendSMSRequest.getMobileNumber());
    }

    @PostMapping(value = "/smscallback", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void smsCallback(@RequestBody MultiValueMap<String, String> map) {
        smsService.receiveSMS(map);
        simpMessagingTemplate.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": Twilio has made a callback request! Here are the contents: " + map.toString());
    }

    private String getTimeStamp() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }
}
