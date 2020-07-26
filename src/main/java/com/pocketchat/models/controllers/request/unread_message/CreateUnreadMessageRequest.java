package com.pocketchat.models.controllers.request.unread_message;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CreateUnreadMessageRequest {

    private String id;

    @NotBlank
    private String conversationId;

    @NotBlank
    private String userId;

    private String lastMessage;

    @NotNull
    private LocalDateTime date;

    private Integer count;

    CreateUnreadMessageRequest(String id, @NotBlank String conversationId, @NotBlank String userId, String lastMessage, @NotNull LocalDateTime date, Integer count) {
        this.id = id;
        this.conversationId = conversationId;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.date = date;
        this.count = count;
    }

    public static CreateUnreadMessageRequestBuilder builder() {
        return new CreateUnreadMessageRequestBuilder();
    }

    public String getId() {
        return this.id;
    }

    public @NotBlank String getConversationId() {
        return this.conversationId;
    }

    public @NotBlank String getUserId() {
        return this.userId;
    }

    public String getLastMessage() {
        return this.lastMessage;
    }

    public @NotNull LocalDateTime getDate() {
        return this.date;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setConversationId(@NotBlank String conversationId) {
        this.conversationId = conversationId;
    }

    public void setUserId(@NotBlank String userId) {
        this.userId = userId;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setDate(@NotNull LocalDateTime date) {
        this.date = date;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CreateUnreadMessageRequest))
            return false;
        final CreateUnreadMessageRequest other = (CreateUnreadMessageRequest) o;
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
        final Object this$date = this.getDate();
        final Object other$date = other.getDate();
        if (this$date == null ? other$date != null : !this$date.equals(other$date)) return false;
        final Object this$count = this.getCount();
        final Object other$count = other.getCount();
        if (this$count == null ? other$count != null : !this$count.equals(other$count)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CreateUnreadMessageRequest;
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
        final Object $date = this.getDate();
        result = result * PRIME + ($date == null ? 43 : $date.hashCode());
        final Object $count = this.getCount();
        result = result * PRIME + ($count == null ? 43 : $count.hashCode());
        return result;
    }

    public String toString() {
        return "CreateUnreadMessageRequest(id=" + this.getId() + ", conversationId=" + this.getConversationId() + ", userId=" + this.getUserId() + ", lastMessage=" + this.getLastMessage() + ", date=" + this.getDate() + ", count=" + this.getCount() + ")";
    }

    public static class CreateUnreadMessageRequestBuilder {
        private String id;
        private @NotBlank String conversationId;
        private @NotBlank String userId;
        private String lastMessage;
        private @NotNull LocalDateTime date;
        private Integer count;

        CreateUnreadMessageRequestBuilder() {
        }

        public CreateUnreadMessageRequest.CreateUnreadMessageRequestBuilder id(String id) {
            this.id = id;
            return this;
        }

        public CreateUnreadMessageRequest.CreateUnreadMessageRequestBuilder conversationId(@NotBlank String conversationId) {
            this.conversationId = conversationId;
            return this;
        }

        public CreateUnreadMessageRequest.CreateUnreadMessageRequestBuilder userId(@NotBlank String userId) {
            this.userId = userId;
            return this;
        }

        public CreateUnreadMessageRequest.CreateUnreadMessageRequestBuilder lastMessage(String lastMessage) {
            this.lastMessage = lastMessage;
            return this;
        }

        public CreateUnreadMessageRequest.CreateUnreadMessageRequestBuilder date(@NotNull LocalDateTime date) {
            this.date = date;
            return this;
        }

        public CreateUnreadMessageRequest.CreateUnreadMessageRequestBuilder count(Integer count) {
            this.count = count;
            return this;
        }

        public CreateUnreadMessageRequest build() {
            return new CreateUnreadMessageRequest(id, conversationId, userId, lastMessage, date, count);
        }

        public String toString() {
            return "CreateUnreadMessageRequest.CreateUnreadMessageRequestBuilder(id=" + this.id + ", conversationId=" + this.conversationId + ", userId=" + this.userId + ", lastMessage=" + this.lastMessage + ", date=" + this.date + ", count=" + this.count + ")";
        }
    }
}
