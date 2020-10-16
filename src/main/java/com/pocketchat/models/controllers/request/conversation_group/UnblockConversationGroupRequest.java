package com.pocketchat.models.controllers.request.conversation_group;

import javax.validation.constraints.NotNull;

public class UnblockConversationGroupRequest {
    @NotNull
    private String conversationGroupBlockId;

    public UnblockConversationGroupRequest() {
    }

    public @NotNull String getConversationGroupBlockId() {
        return this.conversationGroupBlockId;
    }

    public void setConversationGroupBlockId(@NotNull String conversationGroupBlockId) {
        this.conversationGroupBlockId = conversationGroupBlockId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UnblockConversationGroupRequest))
            return false;
        final UnblockConversationGroupRequest other = (UnblockConversationGroupRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$conversationGroupBlockId = this.getConversationGroupBlockId();
        final Object other$conversationGroupBlockId = other.getConversationGroupBlockId();
        if (this$conversationGroupBlockId == null ? other$conversationGroupBlockId != null : !this$conversationGroupBlockId.equals(other$conversationGroupBlockId))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UnblockConversationGroupRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $conversationGroupBlockId = this.getConversationGroupBlockId();
        result = result * PRIME + ($conversationGroupBlockId == null ? 43 : $conversationGroupBlockId.hashCode());
        return result;
    }

    public String toString() {
        return "UnblockConversationGroupRequest(conversationGroupBlockId=" + this.getConversationGroupBlockId() + ")";
    }
}
