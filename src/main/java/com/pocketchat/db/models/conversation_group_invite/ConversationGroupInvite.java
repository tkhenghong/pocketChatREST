package com.pocketchat.db.models.conversation_group_invite;

import com.pocketchat.server.configurations.auditing.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * A model that records the invitation of the conversation group.
 * When a conversation group can be publicly shared(by anyone) to join in, a mechanism needs to be implemented to control.
 * It also needs a expiration date time to prevent overuse of the invitation link to the group.
 * A secret invitation key is made to ensure the security of joining.
 */
@Document(collection = "conversation_group_invite")
public class ConversationGroupInvite extends Auditable {
    @Id
    private String id;

    private String conversationGroupId;

    private LocalDateTime invitationExpireTime;

    private String secretInvitationKey;

    private Integer invitesRemaining;

    ConversationGroupInvite(String id, String conversationGroupId, LocalDateTime invitationExpireTime, String secretInvitationKey, Integer invitesRemaining) {
        this.id = id;
        this.conversationGroupId = conversationGroupId;
        this.invitationExpireTime = invitationExpireTime;
        this.secretInvitationKey = secretInvitationKey;
        this.invitesRemaining = invitesRemaining;
    }

    public static ConversationGroupInviteBuilder builder() {
        return new ConversationGroupInviteBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getConversationGroupId() {
        return this.conversationGroupId;
    }

    public LocalDateTime getInvitationExpireTime() {
        return this.invitationExpireTime;
    }

    public String getSecretInvitationKey() {
        return this.secretInvitationKey;
    }

    public Integer getInvitesRemaining() {
        return this.invitesRemaining;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setConversationGroupId(String conversationGroupId) {
        this.conversationGroupId = conversationGroupId;
    }

    public void setInvitationExpireTime(LocalDateTime invitationExpireTime) {
        this.invitationExpireTime = invitationExpireTime;
    }

    public void setSecretInvitationKey(String secretInvitationKey) {
        this.secretInvitationKey = secretInvitationKey;
    }

    public void setInvitesRemaining(Integer invitesRemaining) {
        this.invitesRemaining = invitesRemaining;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ConversationGroupInvite)) return false;
        final ConversationGroupInvite other = (ConversationGroupInvite) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$conversationGroupId = this.getConversationGroupId();
        final Object other$conversationGroupId = other.getConversationGroupId();
        if (this$conversationGroupId == null ? other$conversationGroupId != null : !this$conversationGroupId.equals(other$conversationGroupId))
            return false;
        final Object this$invitationExpireTime = this.getInvitationExpireTime();
        final Object other$invitationExpireTime = other.getInvitationExpireTime();
        if (this$invitationExpireTime == null ? other$invitationExpireTime != null : !this$invitationExpireTime.equals(other$invitationExpireTime))
            return false;
        final Object this$secretInvitationKey = this.getSecretInvitationKey();
        final Object other$secretInvitationKey = other.getSecretInvitationKey();
        if (this$secretInvitationKey == null ? other$secretInvitationKey != null : !this$secretInvitationKey.equals(other$secretInvitationKey))
            return false;
        final Object this$invitesRemaining = this.getInvitesRemaining();
        final Object other$invitesRemaining = other.getInvitesRemaining();
        if (this$invitesRemaining == null ? other$invitesRemaining != null : !this$invitesRemaining.equals(other$invitesRemaining))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ConversationGroupInvite;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $conversationGroupId = this.getConversationGroupId();
        result = result * PRIME + ($conversationGroupId == null ? 43 : $conversationGroupId.hashCode());
        final Object $invitationExpireTime = this.getInvitationExpireTime();
        result = result * PRIME + ($invitationExpireTime == null ? 43 : $invitationExpireTime.hashCode());
        final Object $secretInvitationKey = this.getSecretInvitationKey();
        result = result * PRIME + ($secretInvitationKey == null ? 43 : $secretInvitationKey.hashCode());
        final Object $invitesRemaining = this.getInvitesRemaining();
        result = result * PRIME + ($invitesRemaining == null ? 43 : $invitesRemaining.hashCode());
        return result;
    }

    public String toString() {
        return "ConversationGroupInvite(id=" + this.getId() + ", conversationGroupId=" + this.getConversationGroupId() + ", invitationExpireTime=" + this.getInvitationExpireTime() + ", secretInvitationKey=" + this.getSecretInvitationKey() + ", invitesRemaining=" + this.getInvitesRemaining() + ")";
    }

    public static class ConversationGroupInviteBuilder {
        private String id;
        private String conversationGroupId;
        private LocalDateTime invitationExpireTime;
        private String secretInvitationKey;
        private Integer invitesRemaining;

        ConversationGroupInviteBuilder() {
        }

        public ConversationGroupInvite.ConversationGroupInviteBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ConversationGroupInvite.ConversationGroupInviteBuilder conversationGroupId(String conversationGroupId) {
            this.conversationGroupId = conversationGroupId;
            return this;
        }

        public ConversationGroupInvite.ConversationGroupInviteBuilder invitationExpireTime(LocalDateTime invitationExpireTime) {
            this.invitationExpireTime = invitationExpireTime;
            return this;
        }

        public ConversationGroupInvite.ConversationGroupInviteBuilder secretInvitationKey(String secretInvitationKey) {
            this.secretInvitationKey = secretInvitationKey;
            return this;
        }

        public ConversationGroupInvite.ConversationGroupInviteBuilder invitesRemaining(Integer invitesRemaining) {
            this.invitesRemaining = invitesRemaining;
            return this;
        }

        public ConversationGroupInvite build() {
            return new ConversationGroupInvite(id, conversationGroupId, invitationExpireTime, secretInvitationKey, invitesRemaining);
        }

        public String toString() {
            return "ConversationGroupInvite.ConversationGroupInviteBuilder(id=" + this.id + ", conversationGroupId=" + this.conversationGroupId + ", invitationExpireTime=" + this.invitationExpireTime + ", secretInvitationKey=" + this.secretInvitationKey + ", invitesRemaining=" + this.invitesRemaining + ")";
        }
    }
}
