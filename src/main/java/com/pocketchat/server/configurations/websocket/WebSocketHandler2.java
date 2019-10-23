package com.pocketchat.server.configurations.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repoServices.conversationGroup.ConversationGroupRepoService;
import com.pocketchat.db.repoServices.userContact.UserContactRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.thymeleaf.util.ObjectUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Working
public class WebSocketHandler2 extends TextWebSocketHandler {

    static List<WebSocketUser> webSocketUserList = new ArrayList<>();
    static List<String> messageList = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();

    private ConversationGroupRepoService conversationGroupRepoService;

    private UserContactRepoService userContactRepoService;


    @Autowired
    WebSocketHandler2(ConversationGroupRepoService conversationGroupRepoService, UserContactRepoService userContactRepoService) {
        this.conversationGroupRepoService = conversationGroupRepoService;
        this.userContactRepoService = userContactRepoService;
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, InterruptedException {
        System.out.println("Text Message received: " + message.getPayload());
        System.out.println("message.getPayloadLength(): " + message.getPayloadLength());
        System.out.println("session.getId(): " + session.getId());
        System.out.println("session.getAcceptedProtocol(): " + session.getAcceptedProtocol());
        System.out.println("session.getBinaryMessageSizeLimit(): " + session.getBinaryMessageSizeLimit());
        System.out.println("session.getLocalAddress().getHostName(): " + session.getLocalAddress().getHostName());
        System.out.println("session.getLocalAddress().getHostString(): " + session.getLocalAddress().getHostString());
        System.out.println("session.getLocalAddress().getPort(): " + session.getLocalAddress().getPort());

        messageList.add(message.getPayload());

        messageList.forEach((String message2) -> {
            System.out.println("message2: " + message2);
        });

        // TODO: Send message to user based on conversation
        // TODO: Check user is online or not to decide send device notification or message
        // TODO: Research on Kafka to create a message system to ensure message is backed up in Kafka

        WebSocketMessage webSocketMessage = convertToWebSocketMessage(message.getPayload());
        // TODO: Make sure frontend and backend object are same, so it can be converted properly.
        if (webSocketMessage != null) {
            session.sendMessage(message); // Send message back to frontend

            if(webSocketMessage.conversationGroup != null) {

            }
            if(webSocketMessage.message != null) {
                Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.findById(webSocketMessage.message.getConversationId());
                if(conversationGroupOptional.isPresent()) {
                    List<UserContact> userContactList = conversationGroupOptional.get().getMemberIds().stream().map((String memberId) -> {
                        Optional<UserContact> userContactOptional = userContactRepoService.findById(memberId);
                        if(userContactOptional.isPresent()) {
                            userContactOptional.get().getUserIds();

                            Optional<User> userOptional;

                        }

                        return userContactOptional.isPresent() ? userContactOptional.get() : null;
                    }).collect(Collectors.toList());


                }
            }
            if(webSocketMessage.multimedia != null) {

            }
            if(webSocketMessage.unreadMessage != null) {

            }
            if(webSocketMessage.user != null) {

            }
            if(webSocketMessage.userContact != null) {

            }
//            session.sendMessage(new TextMessage("Very good."));
        }
        session.sendMessage(message);
    }

    WebSocketMessage convertToWebSocketMessage(String websockeMessageString) {
        WebSocketMessage webSocketMessage;
        try {
            webSocketMessage = objectMapper.readValue(websockeMessageString, WebSocketMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return webSocketMessage;
    }

}
