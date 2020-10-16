package com.pocketchat.models.controllers.request.conversation_group;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class AddConversationGroupMemberRequest {

    @NotEmpty
    @Min(1)
    private List<String> groupMemberIds;

    public AddConversationGroupMemberRequest() {
    }

    public @NotEmpty @Min(1) List<String> getGroupMemberIds() {
        return this.groupMemberIds;
    }

    public void setGroupMemberIds(@NotEmpty @Min(1) List<String> groupMemberIds) {
        this.groupMemberIds = groupMemberIds;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AddConversationGroupMemberRequest))
            return false;
        final AddConversationGroupMemberRequest other = (AddConversationGroupMemberRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$groupMemberIds = this.getGroupMemberIds();
        final Object other$groupMemberIds = other.getGroupMemberIds();
        if (this$groupMemberIds == null ? other$groupMemberIds != null : !this$groupMemberIds.equals(other$groupMemberIds))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AddConversationGroupMemberRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $groupMemberIds = this.getGroupMemberIds();
        result = result * PRIME + ($groupMemberIds == null ? 43 : $groupMemberIds.hashCode());
        return result;
    }

    public String toString() {
        return "AddConversationGroupMemberRequest(groupMemberIds=" + this.getGroupMemberIds() + ")";
    }
}
