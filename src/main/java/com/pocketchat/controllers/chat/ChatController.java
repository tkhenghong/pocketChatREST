package com.pocketchat.controllers.models.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;

//@RestController
@Controller
public class ChatController {

//    private SimpMessagingTemplate simpMessagingTemplate;
//
//    @Autowired
//    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
//        this.simpMessagingTemplate = simpMessagingTemplate;
//    }


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
//    @PostMapping("/testingWEbsocket")
    public String handle(@RequestBody String greeting) {
        System.out.println("ChatController.java Hello");
        Date date = new Date();
        String message = "[" + new Timestamp(date.getTime()) + "]: " + greeting;
        System.out.println("ChatController.java message: " + message);
//        simpMessagingTemplate.convertAndSend("/topic/socket", message);
//        System.out.println("Already done send websocket message");
        return message;
    }

    @MessageMapping("/group/{userId}")
    public void sendJoinGroup(@DestinationVariable String userId, String groupId, Message<?> message) {
        System.out.println("sendJoinGroup()");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    }

}