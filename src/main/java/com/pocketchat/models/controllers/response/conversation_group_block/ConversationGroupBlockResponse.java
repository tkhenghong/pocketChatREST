package com.pocketchat.models.controllers.response.conversation_group_block;

public class ConversationGroupBlockResponse {
    private String id;

    private String userContactId;

    private String conversationGroupId;

    ConversationGroupBlockResponse(String id, String userContactId, String conversationGroupId) {
        this.id = id;
        this.userContactId = userContactId;
        this.conversationGroupId = conversationGroupId;
    }

    public static ConversationGroupBlockResponseBuilder builder() {
        return new ConversationGroupBlockResponseBuilder();
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
        if (!(o instanceof ConversationGroupBlockResponse))
            return false;
        final ConversationGroupBlockResponse other = (ConversationGroupBlockResponse) o;
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
        return other instanceof ConversationGroupBlockResponse;
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
        return "ConversationGroupBlockResponse(id=" + this.getId() + ", userContactId=" + this.getUserContactId() + ", conversationGroupId=" + this.getConversationGroupId() + ")";
    }

    public static class ConversationGroupBlockResponseBuilder {
        private String id;
        private String userContactId;
        private String conversationGroupId;

        ConversationGroupBlockResponseBuilder() {
        }

        public ConversationGroupBlockResponse.ConversationGroupBlockResponseBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ConversationGroupBlockResponse.ConversationGroupBlockResponseBuilder userContactId(String userContactId) {
            this.userContactId = userContactId;
            return this;
        }

        public ConversationGroupBlockResponse.ConversationGroupBlockResponseBuilder conversationGroupId(String conversationGroupId) {
            this.conversationGroupId = conversationGroupId;
            return this;
        }

        public ConversationGroupBlockResponse build() {
            return new ConversationGroupBlockResponse(id, userContactId, conversationGroupId);
        }

        public String toString() {
            return "ConversationGroupBlockResponse.ConversationGroupBlockResponseBuilder(id=" + this.id + ", userContactId=" + this.userContactId + ", conversationGroupId=" + this.conversationGroupId + ")";
        }
    }
}
