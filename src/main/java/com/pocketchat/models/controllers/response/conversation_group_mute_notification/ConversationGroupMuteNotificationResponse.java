package com.pocketchat.models.controllers.response.conversation_group_mute_notification;

import java.time.LocalDateTime;

public class ConversationGroupMuteNotificationResponse {
    private String id;

    private String userContactId;

    private String conversationGroupId;

    private LocalDateTime notificationBlockExpire;

    ConversationGroupMuteNotificationResponse(String id, String userContactId, String conversationGroupId, LocalDateTime notificationBlockExpire) {
        this.id = id;
        this.userContactId = userContactId;
        this.conversationGroupId = conversationGroupId;
        this.notificationBlockExpire = notificationBlockExpire;
    }

    public static ConversationGroupMuteNotificationResponseBuilder builder() {
        return new ConversationGroupMuteNotificationResponseBuilder();
    }

    public String getId() {
        return this.id;
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

    public void setId(String id) {
        this.id = id;
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
        if (!(o instanceof ConversationGroupMuteNotificationResponse))
            return false;
        final ConversationGroupMuteNotificationResponse other = (ConversationGroupMuteNotificationResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
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
        return other instanceof ConversationGroupMuteNotificationResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $userContactId = this.getUserContactId();
        result = result * PRIME + ($userContactId == null ? 43 : $userContactId.hashCode());
        final Object $conversationGroupId = this.getConversationGroupId();
        result = result * PRIME + ($conversationGroupId == null ? 43 : $conversationGroupId.hashCode());
        final Object $notificationBlockExpire = this.getNotificationBlockExpire();
        result = result * PRIME + ($notificationBlockExpire == null ? 43 : $notificationBlockExpire.hashCode());
        return result;
    }

    public String toString() {
        return "ConversationGroupMuteNotificationResponse(id=" + this.getId() + ", userContactId=" + this.getUserContactId() + ", conversationGroupId=" + this.getConversationGroupId() + ", notificationBlockExpire=" + this.getNotificationBlockExpire() + ")";
    }

    public static class ConversationGroupMuteNotificationResponseBuilder {
        private String id;
        private String userContactId;
        private String conversationGroupId;
        private LocalDateTime notificationBlockExpire;

        ConversationGroupMuteNotificationResponseBuilder() {
        }

        public ConversationGroupMuteNotificationResponse.ConversationGroupMuteNotificationResponseBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ConversationGroupMuteNotificationResponse.ConversationGroupMuteNotificationResponseBuilder userContactId(String userContactId) {
            this.userContactId = userContactId;
            return this;
        }

        public ConversationGroupMuteNotificationResponse.ConversationGroupMuteNotificationResponseBuilder conversationGroupId(String conversationGroupId) {
            this.conversationGroupId = conversationGroupId;
            return this;
        }

        public ConversationGroupMuteNotificationResponse.ConversationGroupMuteNotificationResponseBuilder notificationBlockExpire(LocalDateTime notificationBlockExpire) {
            this.notificationBlockExpire = notificationBlockExpire;
            return this;
        }

        public ConversationGroupMuteNotificationResponse build() {
            return new ConversationGroupMuteNotificationResponse(id, userContactId, conversationGroupId, notificationBlockExpire);
        }

        public String toString() {
            return "ConversationGroupMuteNotificationResponse.ConversationGroupMuteNotificationResponseBuilder(id=" + this.id + ", userContactId=" + this.userContactId + ", conversationGroupId=" + this.conversationGroupId + ", notificationBlockExpire=" + this.notificationBlockExpire + ")";
        }
    }
}
