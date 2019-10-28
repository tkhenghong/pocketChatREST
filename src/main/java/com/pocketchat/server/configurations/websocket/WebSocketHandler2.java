package com.pocketchat.server.configurations.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
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

        WebSocketMessage webSocketMessage = convertToWebSocketMessage(textMessage.getPayload());
        // TODO: Make sure frontend and backend object are same, so it can be converted properly.
        if (webSocketMessage != null) {
            // Send message back to frontend
            sendWebSocketMessage(webSocketSession, textMessage);

            if (webSocketMessage.conversationGroup != null) {

            }
            if (webSocketMessage.message != null) {
                // 1. Find WebSocketSession is in webSocketUserList or not. If don't have add create a new WebSocketUser and add it in.
                addWebSocketUser(webSocketSession, webSocketMessage);
                handleWebSocketMessageMessage(webSocketSession, textMessage, webSocketMessage);
            }
            if (webSocketMessage.multimedia != null) {

            }
            if (webSocketMessage.unreadMessage != null) {

            }
            if (webSocketMessage.user != null) {

            }
            if (webSocketMessage.userContact != null) {

            }
        }
    }

    private void addWebSocketUser(WebSocketSession webSocketSession, WebSocketMessage webSocketMessage) {
        boolean webSocketUserExist = isWebSocketUserExist(webSocketSession);
        if (!webSocketUserExist) {
            WebSocketUser newWebSocketUser = new WebSocketUser();
            newWebSocketUser.setWebSocketSession(webSocketSession);
            User user = getUserFromUserId(webSocketMessage.message.getSenderId());
            if (ObjectUtils.isEmpty(user)) {
                throw new UserNotFoundException("User Not found in WebSocket: id: " + webSocketMessage.message.getSenderId());
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
    private void handleWebSocketMessageMessage(WebSocketSession webSocketSession, TextMessage textMessage, WebSocketMessage webSocketMessage) {
        // Get conversationGroup members' ID -> Get userContacts' userID -> Get Users' object
        Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.findById(webSocketMessage.message.getConversationId());
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

    private void sendWebSocketMessage(WebSocketSession webSocketSession, TextMessage textMessage) {
        try {
            webSocketSession.sendMessage(textMessage);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error when trying to send message.");
        }
    }
}
