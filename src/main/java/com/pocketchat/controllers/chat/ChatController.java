package com.pocketchat.controllers.models.chat;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.util.Date;

//@RestController
@Controller
public class ChatController {

    // private SimpMessagingTemplate simpMessagingTemplate;

    // @Autowired
    // public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
    //     this.simpMessagingTemplate = simpMessagingTemplate;
    // }


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    // @PostMapping("/testingWEbsocket")
    public String handle(@RequestBody String greeting) {
        Date date = new Date();
        String message = "[" + new Timestamp(date.getTime()) + "]: " + greeting;
        // simpMessagingTemplate.convertAndSend("/topic/socket", message);
        // System.out.println("Already done send websocket message");
        return message;
    }

    @MessageMapping("/group/{userId}")
    public void sendJoinGroup(@DestinationVariable String userId, String groupId, Message<?> message) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    }

}
