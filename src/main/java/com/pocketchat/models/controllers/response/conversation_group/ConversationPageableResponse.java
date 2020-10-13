package com.pocketchat.models.controllers.response.conversation_group;

import com.pocketchat.models.controllers.response.unread_message.UnreadMessageResponse;
import org.springframework.data.domain.Page;

public class ConversationPageableResponse {
    private Page<ConversationGroupResponse> conversationGroupResponses;
    private Page<UnreadMessageResponse> unreadMessageResponses;

    ConversationPageableResponse(Page<ConversationGroupResponse> conversationGroupResponses, Page<UnreadMessageResponse> unreadMessageResponses) {
        this.conversationGroupResponses = conversationGroupResponses;
        this.unreadMessageResponses = unreadMessageResponses;
    }

    public static ConversationPageableResponseBuilder builder() {
        return new ConversationPageableResponseBuilder();
    }

    public Page<ConversationGroupResponse> getConversationGroupResponses() {
        return this.conversationGroupResponses;
    }

    public Page<UnreadMessageResponse> getUnreadMessageResponses() {
        return this.unreadMessageResponses;
    }

    public void setConversationGroupResponses(Page<ConversationGroupResponse> conversationGroupResponses) {
        this.conversationGroupResponses = conversationGroupResponses;
    }

    public void setUnreadMessageResponses(Page<UnreadMessageResponse> unreadMessageResponses) {
        this.unreadMessageResponses = unreadMessageResponses;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ConversationPageableResponse))
            return false;
        final ConversationPageableResponse other = (ConversationPageableResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$conversationGroupResponses = this.getConversationGroupResponses();
        final Object other$conversationGroupResponses = other.getConversationGroupResponses();
        if (this$conversationGroupResponses == null ? other$conversationGroupResponses != null : !this$conversationGroupResponses.equals(other$conversationGroupResponses))
            return false;
        final Object this$unreadMessageResponses = this.getUnreadMessageResponses();
        final Object other$unreadMessageResponses = other.getUnreadMessageResponses();
        if (this$unreadMessageResponses == null ? other$unreadMessageResponses != null : !this$unreadMessageResponses.equals(other$unreadMessageResponses))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ConversationPageableResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $conversationGroupResponses = this.getConversationGroupResponses();
        result = result * PRIME + ($conversationGroupResponses == null ? 43 : $conversationGroupResponses.hashCode());
        final Object $unreadMessageResponses = this.getUnreadMessageResponses();
        result = result * PRIME + ($unreadMessageResponses == null ? 43 : $unreadMessageResponses.hashCode());
        return result;
    }

    public String toString() {
        return "ConversationPageableResponse(conversationGroupResponses=" + this.getConversationGroupResponses() + ", unreadMessageResponses=" + this.getUnreadMessageResponses() + ")";
    }

    public static class ConversationPageableResponseBuilder {
        private Page<ConversationGroupResponse> conversationGroupResponses;
        private Page<UnreadMessageResponse> unreadMessageResponses;

        ConversationPageableResponseBuilder() {
        }

        public ConversationPageableResponse.ConversationPageableResponseBuilder conversationGroupResponses(Page<ConversationGroupResponse> conversationGroupResponses) {
            this.conversationGroupResponses = conversationGroupResponses;
            return this;
        }

        public ConversationPageableResponse.ConversationPageableResponseBuilder unreadMessageResponses(Page<UnreadMessageResponse> unreadMessageResponses) {
            this.unreadMessageResponses = unreadMessageResponses;
            return this;
        }

        public ConversationPageableResponse build() {
            return new ConversationPageableResponse(conversationGroupResponses, unreadMessageResponses);
        }

        public String toString() {
            return "ConversationPageableResponse.ConversationPageableResponseBuilder(conversationGroupResponses=" + this.conversationGroupResponses + ", unreadMessageResponses=" + this.unreadMessageResponses + ")";
        }
    }
}
