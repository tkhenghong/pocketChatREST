package com.pocketchat.models.controllers.request.conversation_group;

import java.time.LocalDateTime;

/**
 * NOTE: This is for the Service layer.
 */
public class CreateConversationGroupMuteNotificationRequest {

    private String userContactId;

    private String conversationGroupId;

    private LocalDateTime notificationBlockExpire;

    CreateConversationGroupMuteNotificationRequest(String userContactId, String conversationGroupId, LocalDateTime notificationBlockExpire) {
        this.userContactId = userContactId;
        this.conversationGroupId = conversationGroupId;
        this.notificationBlockExpire = notificationBlockExpire;
    }

    public static CreateConversationGroupMuteNotificationRequestBuilder builder() {
        return new CreateConversationGroupMuteNotificationRequestBuilder();
    }

    public String getUserContactId() {
        return this.userContactId;
    }

    public String getConversationGroupId() {
        return this.conversationGroupId;
    }

    public LocalDateTime getNotificationBlockExpire() {
        return this.notificationBlockExpire;
    }

    public void setUserContactId(String userContactId) {
        this.userContactId = userContactId;
    }

    public void setConversationGroupId(String conversationGroupId) {
        this.conversationGroupId = conversationGroupId;
    }

    public void setNotificationBlockExpire(LocalDateTime notificationBlockExpire) {
        this.notificationBlockExpire = notificationBlockExpire;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CreateConversationGroupMuteNotificationRequest))
            return false;
        final CreateConversationGroupMuteNotificationRequest other = (CreateConversationGroupMuteNotificationRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$userContactId = this.getUserContactId();
        final Object other$userContactId = other.getUserContactId();
        if (this$userContactId == null ? other$userContactId != null : !this$userContactId.equals(other$userContactId))
            return false;
        final Object this$conversationGroupId = this.getConversationGroupId();
        final Object other$conversationGroupId = other.getConversationGroupId();
        if (this$conversationGroupId == null ? other$conversationGroupId != null : !this$conversationGroupId.equals(other$conversationGroupId))
            return false;
        final Object this$notificationBlockExpire = this.getNotificationBlockExpire();
        final Object other$notificationBlockExpire = other.getNotificationBlockExpire();
        if (this$notificationBlockExpire == null ? other$notificationBlockExpire != null : !this$notificationBlockExpire.equals(other$notificationBlockExpire))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CreateConversationGroupMuteNotificationRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $userContactId = this.getUserContactId();
        result = result * PRIME + ($userContactId == null ? 43 : $userContactId.hashCode());
        final Object $conversationGroupId = this.getConversationGroupId();
        result = result * PRIME + ($conversationGroupId == null ? 43 : $conversationGroupId.hashCode());
        final Object $notificationBlockExpire = this.getNotificationBlockExpire();
        result = result * PRIME + ($notificationBlockExpire == null ? 43 : $notificationBlockExpire.hashCode());
        return result;
    }

    public String toString() {
        return "CreateConversationGroupMuteNotificationRequest(userContactId=" + this.getUserContactId() + ", conversationGroupId=" + this.getConversationGroupId() + ", notificationBlockExpire=" + this.getNotificationBlockExpire() + ")";
    }

    public static class CreateConversationGroupMuteNotificationRequestBuilder {
        private String userContactId;
        private String conversationGroupId;
        private LocalDateTime notificationBlockExpire;

        CreateConversationGroupMuteNotificationRequestBuilder() {
        }

        public CreateConversationGroupMuteNotificationRequest.CreateConversationGroupMuteNotificationRequestBuilder userContactId(String userContactId) {
            this.userContactId = userContactId;
            return this;
        }

        public CreateConversationGroupMuteNotificationRequest.CreateConversationGroupMuteNotificationRequestBuilder conversationGroupId(String conversationGroupId) {
            this.conversationGroupId = conversationGroupId;
            return this;
        }

        public CreateConversationGroupMuteNotificationRequest.CreateConversationGroupMuteNotificationRequestBuilder notificationBlockExpire(LocalDateTime notificationBlockExpire) {
            this.notificationBlockExpire = notificationBlockExpire;
            return this;
        }

        public CreateConversationGroupMuteNotificationRequest build() {
            return new CreateConversationGroupMuteNotificationRequest(userContactId, conversationGroupId, notificationBlockExpire);
        }

        public String toString() {
            return "CreateConversationGroupMuteNotificationRequest.CreateConversationGroupMuteNotificationRequestBuilder(userContactId=" + this.userContactId + ", conversationGroupId=" + this.conversationGroupId + ", notificationBlockExpire=" + this.notificationBlockExpire + ")";
        }
    }
}
