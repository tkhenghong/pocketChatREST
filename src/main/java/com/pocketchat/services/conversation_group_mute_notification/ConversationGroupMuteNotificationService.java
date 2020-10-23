package com.pocketchat.services.conversation_group_mute_notification;

import com.pocketchat.db.models.conversation_group_mute_notification.ConversationGroupMuteNotification;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupMuteNotificationRequest;
import com.pocketchat.models.controllers.response.conversation_group_mute_notification.ConversationGroupMuteNotificationResponse;

public interface ConversationGroupMuteNotificationService {

    ConversationGroupMuteNotification addConversationGroupMuteNotification(CreateConversationGroupMuteNotificationRequest createConversationGroupMuteNotificationRequest);

    boolean isConversationGroupMuted(String userContactId, String conversationGroupId);

    ConversationGroupMuteNotification getSingleConversationGroupMuteNotification(String conversationGroupMuteNotificationId);

    void deleteConversationGroupMuteNotification(String conversationGroupMuteNotificationId);

    ConversationGroupMuteNotificationResponse conversationGroupMuteNotificationResponseMapper(ConversationGroupMuteNotification conversationGroupMuteNotification);
}