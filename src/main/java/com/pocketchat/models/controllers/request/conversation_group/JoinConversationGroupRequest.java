package com.pocketchat.models.controllers.request.conversation_group;

import javax.validation.constraints.NotBlank;

public class JoinConversationGroupRequest {

    @NotBlank
    private String inviteCode;

    public JoinConversationGroupRequest() {
    }

    public @NotBlank String getInviteCode() {
        return this.inviteCode;
    }

    public void setInviteCode(@NotBlank String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof JoinConversationGroupRequest))
            return false;
        final JoinConversationGroupRequest other = (JoinConversationGroupRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$inviteCode = this.getInviteCode();
        final Object other$inviteCode = other.getInviteCode();
        if (this$inviteCode == null ? other$inviteCode != null : !this$inviteCode.equals(other$inviteCode))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof JoinConversationGroupRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $inviteCode = this.getInviteCode();
        result = result * PRIME + ($inviteCode == null ? 43 : $inviteCode.hashCode());
        return result;
    }

    public String toString() {
        return "JoinConversationGroupRequest(inviteCode=" + this.getInviteCode() + ")";
    }
}
