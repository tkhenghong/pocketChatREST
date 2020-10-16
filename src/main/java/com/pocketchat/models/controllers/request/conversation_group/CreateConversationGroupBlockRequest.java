package com.pocketchat.models.controllers.request.conversation_group;

public class CreateConversationGroupBlockRequest {

    private String userContactId;

    private String conversationGroupId;

    CreateConversationGroupBlockRequest(String userContactId, String conversationGroupId) {
        this.userContactId = userContactId;
        this.conversationGroupId = conversationGroupId;
    }

    public static CreateConversationGroupBlockRequestBuilder builder() {
        return new CreateConversationGroupBlockRequestBuilder();
    }

    public String getUserContactId() {
        return this.userContactId;
    }

    public String getConversationGroupId() {
        return this.conversationGroupId;
    }

    public void setUserContactId(String userContactId) {
        this.userContactId = userContactId;
    }

    public void setConversationGroupId(String conversationGroupId) {
        this.conversationGroupId = conversationGroupId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CreateConversationGroupBlockRequest))
            return false;
        final CreateConversationGroupBlockRequest other = (CreateConversationGroupBlockRequest) o;
        if (!other.canEqual((Object) this)) return false;
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
        return other instanceof CreateConversationGroupBlockRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $userContactId = this.getUserContactId();
        result = result * PRIME + ($userContactId == null ? 43 : $userContactId.hashCode());
        final Object $conversationGroupId = this.getConversationGroupId();
        result = result * PRIME + ($conversationGroupId == null ? 43 : $conversationGroupId.hashCode());
        return result;
    }

    public String toString() {
        return "CreateConversationGroupBlockRequest(userContactId=" + this.getUserContactId() + ", conversationGroupId=" + this.getConversationGroupId() + ")";
    }

    public static class CreateConversationGroupBlockRequestBuilder {
        private String userContactId;
        private String conversationGroupId;

        CreateConversationGroupBlockRequestBuilder() {
        }

        public CreateConversationGroupBlockRequest.CreateConversationGroupBlockRequestBuilder userContactId(String userContactId) {
            this.userContactId = userContactId;
            return this;
        }

        public CreateConversationGroupBlockRequest.CreateConversationGroupBlockRequestBuilder conversationGroupId(String conversationGroupId) {
            this.conversationGroupId = conversationGroupId;
            return this;
        }

        public CreateConversationGroupBlockRequest build() {
            return new CreateConversationGroupBlockRequest(userContactId, conversationGroupId);
        }

        public String toString() {
            return "CreateConversationGroupBlockRequest.CreateConversationGroupBlockRequestBuilder(userContactId=" + this.userContactId + ", conversationGroupId=" + this.conversationGroupId + ")";
        }
    }
}
