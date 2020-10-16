package com.pocketchat.services.conversation_group_mute_notification;

import com.pocketchat.db.models.conversation_group_mute_notification.ConversationGroupMuteNotification;
import com.pocketchat.db.repo_services.conversation_group_mute_notification.ConversationGroupMuteNotificationRepoService;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupMuteNotificationRequest;
import com.pocketchat.models.controllers.response.conversation_group_mute_notification.ConversationGroupMuteNotificationResponse;
import com.pocketchat.server.exceptions.conversation_group_mute_notification.ConversationGroupMuteNotificationNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConversationGroupMuteNotificationServiceImpl implements ConversationGroupMuteNotificationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConversationGroupMuteNotificationRepoService conversationGroupMuteNotificationRepoService;

    @Autowired
    public ConversationGroupMuteNotificationServiceImpl(ConversationGroupMuteNotificationRepoService conversationGroupMuteNotificationRepoService) {
        this.conversationGroupMuteNotificationRepoService = conversationGroupMuteNotificationRepoService;
    }

    @Override
    public ConversationGroupMuteNotification addConversationGroupMuteNotification(CreateConversationGroupMuteNotificationRequest createConversationGroupMuteNotificationRequest) {
        ConversationGroupMuteNotification conversationGroupMuteNotification = createConversationGroupMuteNotificationRequestToConversationGroupMuteNotification(createConversationGroupMuteNotificationRequest);
        return conversationGroupMuteNotificationRepoService.save(conversationGroupMuteNotification);
    }

    @Override
    public boolean isConversationGroupMuted(String userContactId, String conversationGroupId) {
        return conversationGroupMuteNotificationRepoService.existUserContactIdAndConversationGroupId(userContactId, conversationGroupId);
    }

    @Override
    public ConversationGroupMuteNotification getSingleConversationGroupMuteNotification(String conversationGroupMuteNotificationId) {
        Optional<ConversationGroupMuteNotification> conversationGroupMuteNotificationOptional = conversationGroupMuteNotificationRepoService.findById(conversationGroupMuteNotificationId);

        if (conversationGroupMuteNotificationOptional.isEmpty()) {
            throw new ConversationGroupMuteNotificationNotFoundException("Unable to find any mute record from the conversationGroupMuteNotificationId: " + conversationGroupMuteNotificationId);
        }

        return conversationGroupMuteNotificationOptional.get();
    }

    @Override
    public void deleteConversationGroupMuteNotification(String conversationGroupMuteNotificationId) {
        conversationGroupMuteNotificationRepoService.deleteById(conversationGroupMuteNotificationId);
    }

    private ConversationGroupMuteNotification createConversationGroupMuteNotificationRequestToConversationGroupMuteNotification(CreateConversationGroupMuteNotificationRequest createConversationGroupMuteNotificationRequest) {
        return ConversationGroupMuteNotification.builder()
                .userContactId(createConversationGroupMuteNotificationRequest.getUserContactId())
                .conversationGroupId(createConversationGroupMuteNotificationRequest.getConversationGroupId())
                .notificationBlockExpire(createConversationGroupMuteNotificationRequest.getNotificationBlockExpire())
                .build();
    }

    @Override
    public ConversationGroupMuteNotificationResponse conversationGroupMuteNotificationResponseMapper(ConversationGroupMuteNotification conversationGroupMuteNotification) {
        return ConversationGroupMuteNotificationResponse.builder()
                .id(conversationGroupMuteNotification.getId())
                .userContactId(conversationGroupMuteNotification.getUserContactId())
                .conversationGroupId(conversationGroupMuteNotification.getConversationGroupId())
                .notificationBlockExpire(conversationGroupMuteNotification.getNotificationBlockExpire())
                .build();
    }
}
