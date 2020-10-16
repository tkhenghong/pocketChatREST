package com.pocketchat.models.controllers.request.conversation_group;

public class UnmuteConversationGroupNotificationRequest {
    private String conversationGroupMuteNotificationId;

    public UnmuteConversationGroupNotificationRequest() {
    }

    public String getConversationGroupMuteNotificationId() {
        return this.conversationGroupMuteNotificationId;
    }

    public void setConversationGroupMuteNotificationId(String conversationGroupMuteNotificationId) {
        this.conversationGroupMuteNotificationId = conversationGroupMuteNotificationId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UnmuteConversationGroupNotificationRequest))
            return false;
        final UnmuteConversationGroupNotificationRequest other = (UnmuteConversationGroupNotificationRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$conversationGroupMuteNotificationId = this.getConversationGroupMuteNotificationId();
        final Object other$conversationGroupMuteNotificationId = other.getConversationGroupMuteNotificationId();
        if (this$conversationGroupMuteNotificationId == null ? other$conversationGroupMuteNotificationId != null : !this$conversationGroupMuteNotificationId.equals(other$conversationGroupMuteNotificationId))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UnmuteConversationGroupNotificationRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $conversationGroupMuteNotificationId = this.getConversationGroupMuteNotificationId();
        result = result * PRIME + ($conversationGroupMuteNotificationId == null ? 43 : $conversationGroupMuteNotificationId.hashCode());
        return result;
    }

    public String toString() {
        return "UnmuteConversationGroupNotificationRequest(conversationGroupMuteNotificationId=" + this.getConversationGroupMuteNotificationId() + ")";
    }
}
