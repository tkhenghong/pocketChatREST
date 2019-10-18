//package com.pocketchat.controllers.models.chat;
//
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.stereotype.Controller;
//
//
//// Tutorial link: https://www.callicoder.com/spring-boot-websocket-chat-example/
//@Controller
//public class ChatController {
//
//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
//    public String sendMessage(@Payload String message) {
//        return message;
//    }
//
//    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/public")
//    public String addUser(@Payload String message,
//                               SimpMessageHeaderAccessor headerAccessor) {
//        // Add username in web socket session
//        headerAccessor.getSessionAttributes().put("username", message);
//        return message;
//    }
//
//}
