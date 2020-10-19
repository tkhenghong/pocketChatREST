package com.pocketchat.db.models.conversation_group_block;

import com.pocketchat.server.configurations.auditing.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * A model to store the record when a person wants to block another conversation group from sending him/her messages.
 * For example, User A wants to block User B conversation. so a record is made here. Then, when User B goes to the
 * conversation group of User A, the front end will check with backend whether the conversation group is blocked or not.
 *
 */
@Document(collection = "conversation_group_block")
public class ConversationGroupBlock extends Auditable {
    @Id
    private String id;

    private String userContactId;

    private String conversationGroupId;

    ConversationGroupBlock(String id, String userContactId, String conversationGroupId) {
        this.id = id;
        this.userContactId = userContactId;
        this.conversationGroupId = conversationGroupId;
    }

    public static ConversationGroupBlockBuilder builder() {
        return new ConversationGroupBlockBuilder();
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

    public void setId(String id) {
        this.id = id;
    }

    public void setUserContactId(String userContactId) {
        this.userContactId = userContactId;
    }

    public void setConversationGroupId(String conversationGroupId) {
        this.conversationGroupId = conversationGroupId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ConversationGroupBlock)) return false;
        final ConversationGroupBlock other = (ConversationGroupBlock) o;
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
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ConversationGroupBlock;
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
        return result;
    }

    public String toString() {
        return "ConversationGroupBlock(id=" + this.getId() + ", userContactId=" + this.getUserContactId() + ", conversationGroupId=" + this.getConversationGroupId() + ")";
    }

    public static class ConversationGroupBlockBuilder {
        private String id;
        private String userContactId;
        private String conversationGroupId;

        ConversationGroupBlockBuilder() {
        }

        public ConversationGroupBlock.ConversationGroupBlockBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ConversationGroupBlock.ConversationGroupBlockBuilder userContactId(String userContactId) {
            this.userContactId = userContactId;
            return this;
        }

        public ConversationGroupBlock.ConversationGroupBlockBuilder conversationGroupId(String conversationGroupId) {
            this.conversationGroupId = conversationGroupId;
            return this;
        }

        public ConversationGroupBlock build() {
            return new ConversationGroupBlock(id, userContactId, conversationGroupId);
        }

        public String toString() {
            return "ConversationGroupBlock.ConversationGroupBlockBuilder(id=" + this.id + ", userContactId=" + this.userContactId + ", conversationGroupId=" + this.conversationGroupId + ")";
        }
    }
}
