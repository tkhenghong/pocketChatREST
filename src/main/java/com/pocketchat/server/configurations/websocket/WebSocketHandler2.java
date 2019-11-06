package com.pocketchat.server.configurations.websocket;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.message.Message;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repoServices.conversationGroup.ConversationGroupRepoService;
import com.pocketchat.db.repoServices.user.UserRepoService;
import com.pocketchat.db.repoServices.userContact.UserContactRepoService;
import com.pocketchat.server.exceptions.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

// Working
public class WebSocketHandler2 extends TextWebSocketHandler {

    static List<WebSocketUser> webSocketUserList = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ConversationGroupRepoService conversationGroupRepoService;

    @Autowired
    private UserContactRepoService userContactRepoService;

    @Autowired
    private UserRepoService userRepoService;

    @Override
    public void handleTextMessage(WebSocketSession webSocketSession, TextMessage textMessage) {
        System.out.println("Text Message received: " + textMessage.getPayload());
        System.out.println("textMessage.getPayloadLength(): " + textMessage.getPayloadLength());
        System.out.println("webSocketSession.getId(): " + webSocketSession.getId());
        System.out.println("webSocketSession.getAcceptedProtocol(): " + webSocketSession.getAcceptedProtocol());
        System.out.println("webSocketSession.getBinaryMessageSizeLimit(): " + webSocketSession.getBinaryMessageSizeLimit());
        System.out.println("webSocketSession.getLocalAddress().getHostName(): " + webSocketSession.getLocalAddress().getHostName());
        System.out.println("webSocketSession.getLocalAddress().getHostString(): " + webSocketSession.getLocalAddress().getHostString());
        System.out.println("webSocketSession.getLocalAddress().getPort(): " + webSocketSession.getLocalAddress().getPort());

        // TODO: Research on Kafka to create a message system to ensure message is backed up in Kafka

        CustomizedWebSocketMessage customizedWebSocketMessage = convertToCustomizedWebSocketMessage(textMessage.getPayload());
        // TODO: Make sure frontend and backend object are same, so it can be converted properly.
        if (customizedWebSocketMessage.toString() != null) {
            // Send message back to frontend
            sendWebSocketMessage(webSocketSession, textMessage);

            if (!StringUtils.isEmptyOrWhitespace(customizedWebSocketMessage.getConversationGroup())) {

            }
            if (!StringUtils.isEmptyOrWhitespace(customizedWebSocketMessage.getMessage())) {
                Message message = convertToMessage(customizedWebSocketMessage.getMessage());
                // 1. Find WebSocketSession is in webSocketUserList or not. If don't have add create a new WebSocketUser and add it in.
                addWebSocketUser(webSocketSession, message.getSenderId());
                handleWebSocketMessageMessage(webSocketSession, textMessage, message);
            }
            if (!StringUtils.isEmptyOrWhitespace(customizedWebSocketMessage.getMultimedia())) {

            }
            if (!StringUtils.isEmptyOrWhitespace(customizedWebSocketMessage.getUnreadMessage())) {

            }
            if (!StringUtils.isEmptyOrWhitespace(customizedWebSocketMessage.getUser())) {

            }
            if (!StringUtils.isEmptyOrWhitespace(customizedWebSocketMessage.getUserContact())) {

            }
        }
    }

    private void addWebSocketUser(WebSocketSession webSocketSession, String webSocketUserId) {
        boolean webSocketUserExist = isWebSocketUserExist(webSocketSession);
        if (!webSocketUserExist) {
            WebSocketUser newWebSocketUser = new WebSocketUser();
            newWebSocketUser.setWebSocketSession(webSocketSession);
            User user = getUserFromUserId(webSocketUserId);
            if (ObjectUtils.isEmpty(user)) {
                throw new UserNotFoundException("User Not found in WebSocket: id: " + webSocketUserId);
            }
            newWebSocketUser.setUser(user);
            webSocketUserList.add(newWebSocketUser);
        }
    }

    private boolean isWebSocketUserExist(WebSocketSession webSocketSession) {
        AtomicBoolean webSocketUserFound = new AtomicBoolean(false);
        if (!ObjectUtils.isEmpty(webSocketSession)) {
            webSocketUserList.forEach((WebSocketUser existingWebSocketUser) -> {
                if (existingWebSocketUser.getWebSocketSession().getId().equals(webSocketSession.getId())) {
                    webSocketUserFound.set(true);
                }
            });
        }
        return webSocketUserFound.get();
    }

    // 1. Find the conversationGroup
    // 2. Find the userContacts
    // 3. Find the Users. Only send message to those Users who registered into this system.
    // 4. Check those Users are online or not, if yes send that message to the WebSocketSession that User linked to. If no then send it a notification message.
    // Possibilities to solve:
    // 1. 1 user own many devices is connected to WebSocket
    // TODO: Store unactive Users to Kafka
    private void handleWebSocketMessageMessage(WebSocketSession webSocketSession, TextMessage textMessage, Message message) {
        // Get conversationGroup members' ID -> Get userContacts' userID -> Get Users' object
        Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.findById(message.getConversationId());
        List<User> userList;
        if (conversationGroupOptional.isPresent()) {
            userList = getAllUsersFromUserContactIds(conversationGroupOptional.get());

            userList.forEach(user -> {
                List<WebSocketUser> webSocketUserResultList = getWebSocketUsers(user);
                if (!webSocketUserResultList.isEmpty()) {
                    webSocketUserResultList.forEach((WebSocketUser webSocketUser) -> {
                        sendWebSocketMessage(webSocketSession, textMessage);
                    });
                }
            });
        }
    }

    private User getUserFromUserId(String userId) {
        Optional<User> userOptional = userRepoService.findById(userId);
        return userOptional.orElse(null);
    }

    // Gets Users from UserContacts. Ignore those Users who doesn't have userId (Strangers, those who haven't register yet)
    private List<User> getAllUsersFromUserContactIds(ConversationGroup conversationGroup) {
        List<UserContact> userContactList = userContactRepoService.findByUserIdIn(conversationGroup.getMemberIds());
        List<String> userIds = userContactList.stream().map(UserContact::getUserId).collect(Collectors.toList());
        List<User> userList1 = userRepoService.findByIdsIn(userIds);

        return userList1;
    }

    // If found user is online , return the object
    // If not found, return null to signify not online (should send notifications and store the unsent message in Kafka, when get userIsOnline event, send the unsent message)
    private List<WebSocketUser> getWebSocketUsers(User user) {
        List<WebSocketUser> webSocketUserResultList = new ArrayList<>();

        webSocketUserList.forEach((WebSocketUser existingWebSocketUser) -> {
            if (existingWebSocketUser.getUser().getId().equals(user.getId())) {
                webSocketUserResultList.add(existingWebSocketUser);
            }
        });

        return webSocketUserResultList;
    }

    CustomizedWebSocketMessage convertToCustomizedWebSocketMessage(String websocketMessageString) {
        CustomizedWebSocketMessage customizedWebSocketMessage;
        try {
            customizedWebSocketMessage = objectMapper.readValue(websocketMessageString, CustomizedWebSocketMessage.class);
            System.out.println("customizedWebSocketMessage: " + customizedWebSocketMessage.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return customizedWebSocketMessage;
    }

    private Message convertToMessage(String messageString) {
        Message message;
        try {
            objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            message = objectMapper.readValue(messageString, Message.class);
            System.out.println("message.getId(): " + message.getId());
            System.out.println("message.getConversationId(): " + message.getConversationId());
            System.out.println("message.getMessageContent(): " + message.getMessageContent());
            System.out.println("message.getMultimediaId(): " + message.getMultimediaId());
            System.out.println("message.getReceiverId(): " + message.getReceiverId());
            System.out.println("message.getReceiverMobileNo(): " + message.getReceiverMobileNo());
            System.out.println("message.getReceiverName(): " + message.getReceiverName());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return message;
    }

    private void sendWebSocketMessage(WebSocketSession webSocketSession, TextMessage textMessage) {
        try {
            webSocketSession.sendMessage(textMessage);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error when trying to send message.");
        }
    }
}
