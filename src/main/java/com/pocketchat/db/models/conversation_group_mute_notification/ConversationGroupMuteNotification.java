package com.pocketchat.db.models.conversation_group_mute_notification;

import com.pocketchat.server.configurations.auditing.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * A model to store a record of a person mute a conversation group's notification.
 */
@Document(collection = "conversation_group_mute_notification")
public class ConversationGroupMuteNotification extends Auditable {
    @Id
    private String id;

    private String userContactId;

    private String conversationGroupId;

    private LocalDateTime notificationBlockExpire;

    ConversationGroupMuteNotification(String id, String userContactId, String conversationGroupId, LocalDateTime notificationBlockExpire) {
        this.id = id;
        this.userContactId = userContactId;
        this.conversationGroupId = conversationGroupId;
        this.notificationBlockExpire = notificationBlockExpire;
    }

    public static ConversationGroupMuteNotificationBuilder builder() {
        return new ConversationGroupMuteNotificationBuilder();
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
        if (!(o instanceof ConversationGroupMuteNotification))
            return false;
        final ConversationGroupMuteNotification other = (ConversationGroupMuteNotification) o;
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
        return other instanceof ConversationGroupMuteNotification;
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
        return "ConversationGroupMuteNotification(id=" + this.getId() + ", userContactId=" + this.getUserContactId() + ", conversationGroupId=" + this.getConversationGroupId() + ", notificationBlockExpire=" + this.getNotificationBlockExpire() + ")";
    }

    public static class ConversationGroupMuteNotificationBuilder {
        private String id;
        private String userContactId;
        private String conversationGroupId;
        private LocalDateTime notificationBlockExpire;

        ConversationGroupMuteNotificationBuilder() {
        }

        public ConversationGroupMuteNotification.ConversationGroupMuteNotificationBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ConversationGroupMuteNotification.ConversationGroupMuteNotificationBuilder userContactId(String userContactId) {
            this.userContactId = userContactId;
            return this;
        }

        public ConversationGroupMuteNotification.ConversationGroupMuteNotificationBuilder conversationGroupId(String conversationGroupId) {
            this.conversationGroupId = conversationGroupId;
            return this;
        }

        public ConversationGroupMuteNotification.ConversationGroupMuteNotificationBuilder notificationBlockExpire(LocalDateTime notificationBlockExpire) {
            this.notificationBlockExpire = notificationBlockExpire;
            return this;
        }

        public ConversationGroupMuteNotification build() {
            return new ConversationGroupMuteNotification(id, userContactId, conversationGroupId, notificationBlockExpire);
        }

        public String toString() {
            return "ConversationGroupMuteNotification.ConversationGroupMuteNotificationBuilder(id=" + this.id + ", userContactId=" + this.userContactId + ", conversationGroupId=" + this.conversationGroupId + ", notificationBlockExpire=" + this.notificationBlockExpire + ")";
        }
    }
}
