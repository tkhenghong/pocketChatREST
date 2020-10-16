package com.pocketchat.models.controllers.request.conversation_group;

import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public class GetConversationGroupsRequest {
    private String conversationGroupName;

    @NotNull
    private Pageable pageable;

    public GetConversationGroupsRequest() {
    }

    public String getConversationGroupName() {
        return this.conversationGroupName;
    }

    public Pageable getPageable() {
        return this.pageable;
    }

    public void setConversationGroupName(String conversationGroupName) {
        this.conversationGroupName = conversationGroupName;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof GetConversationGroupsRequest))
            return false;
        final GetConversationGroupsRequest other = (GetConversationGroupsRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$conversationGroupName = this.getConversationGroupName();
        final Object other$conversationGroupName = other.getConversationGroupName();
        if (this$conversationGroupName == null ? other$conversationGroupName != null : !this$conversationGroupName.equals(other$conversationGroupName))
            return false;
        final Object this$pageable = this.getPageable();
        final Object other$pageable = other.getPageable();
        if (this$pageable == null ? other$pageable != null : !this$pageable.equals(other$pageable)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GetConversationGroupsRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $conversationGroupName = this.getConversationGroupName();
        result = result * PRIME + ($conversationGroupName == null ? 43 : $conversationGroupName.hashCode());
        final Object $pageable = this.getPageable();
        result = result * PRIME + ($pageable == null ? 43 : $pageable.hashCode());
        return result;
    }

    public String toString() {
        return "GetConversationGroupsRequest(conversationGroupName=" + this.getConversationGroupName() + ", pageable=" + this.getPageable() + ")";
    }
}
