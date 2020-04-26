package com.pocketchat.models.controllers.response.unreadMessage;

public class UnreadMessageResponse {
    private String id;

    private String conversationId;

    private String userId;

    private String lastMessage;

    private long date;

    private int count;

    UnreadMessageResponse(String id, String conversationId, String userId, String lastMessage, long date, int count) {
        this.id = id;
        this.conversationId = conversationId;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.date = date;
        this.count = count;
    }

    public static UnreadMessageResponseBuilder builder() {
        return new UnreadMessageResponseBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getConversationId() {
        return this.conversationId;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getLastMessage() {
        return this.lastMessage;
    }

    public long getDate() {
        return this.date;
    }

    public int getCount() {
        return this.count;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UnreadMessageResponse))
            return false;
        final UnreadMessageResponse other = (UnreadMessageResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$conversationId = this.getConversationId();
        final Object other$conversationId = other.getConversationId();
        if (this$conversationId == null ? other$conversationId != null : !this$conversationId.equals(other$conversationId))
            return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$lastMessage = this.getLastMessage();
        final Object other$lastMessage = other.getLastMessage();
        if (this$lastMessage == null ? other$lastMessage != null : !this$lastMessage.equals(other$lastMessage))
            return false;
        if (this.getDate() != other.getDate()) return false;
        if (this.getCount() != other.getCount()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UnreadMessageResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $conversationId = this.getConversationId();
        result = result * PRIME + ($conversationId == null ? 43 : $conversationId.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $lastMessage = this.getLastMessage();
        result = result * PRIME + ($lastMessage == null ? 43 : $lastMessage.hashCode());
        final long $date = this.getDate();
        result = result * PRIME + (int) ($date >>> 32 ^ $date);
        result = result * PRIME + this.getCount();
        return result;
    }

    public String toString() {
        return "UnreadMessageResponse(id=" + this.getId() + ", conversationId=" + this.getConversationId() + ", userId=" + this.getUserId() + ", lastMessage=" + this.getLastMessage() + ", date=" + this.getDate() + ", count=" + this.getCount() + ")";
    }

    public static class UnreadMessageResponseBuilder {
        private String id;
        private String conversationId;
        private String userId;
        private String lastMessage;
        private long date;
        private int count;

        UnreadMessageResponseBuilder() {
        }

        public UnreadMessageResponse.UnreadMessageResponseBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UnreadMessageResponse.UnreadMessageResponseBuilder conversationId(String conversationId) {
            this.conversationId = conversationId;
            return this;
        }

        public UnreadMessageResponse.UnreadMessageResponseBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public UnreadMessageResponse.UnreadMessageResponseBuilder lastMessage(String lastMessage) {
            this.lastMessage = lastMessage;
            return this;
        }

        public UnreadMessageResponse.UnreadMessageResponseBuilder date(long date) {
            this.date = date;
            return this;
        }

        public UnreadMessageResponse.UnreadMessageResponseBuilder count(int count) {
            this.count = count;
            return this;
        }

        public UnreadMessageResponse build() {
            return new UnreadMessageResponse(id, conversationId, userId, lastMessage, date, count);
        }

        public String toString() {
            return "UnreadMessageResponse.UnreadMessageResponseBuilder(id=" + this.id + ", conversationId=" + this.conversationId + ", userId=" + this.userId + ", lastMessage=" + this.lastMessage + ", date=" + this.date + ", count=" + this.count + ")";
        }
    }
}
