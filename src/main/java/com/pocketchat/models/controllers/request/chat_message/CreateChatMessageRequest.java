package com.pocketchat.models.controllers.request.chat_message;

import javax.validation.constraints.NotBlank;

public class CreateChatMessageRequest {
    @NotBlank
    private String conversationId;

    @NotBlank
    private String messageContent;

    CreateChatMessageRequest(@NotBlank String conversationId, @NotBlank String messageContent) {
        this.conversationId = conversationId;
        this.messageContent = messageContent;
    }

    public static CreateChatMessageRequestBuilder builder() {
        return new CreateChatMessageRequestBuilder();
    }

    public @NotBlank String getConversationId() {
        return this.conversationId;
    }

    public @NotBlank String getMessageContent() {
        return this.messageContent;
    }

    public void setConversationId(@NotBlank String conversationId) {
        this.conversationId = conversationId;
    }

    public void setMessageContent(@NotBlank String messageContent) {
        this.messageContent = messageContent;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CreateChatMessageRequest))
            return false;
        final CreateChatMessageRequest other = (CreateChatMessageRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$conversationId = this.getConversationId();
        final Object other$conversationId = other.getConversationId();
        if (this$conversationId == null ? other$conversationId != null : !this$conversationId.equals(other$conversationId))
            return false;
        final Object this$messageContent = this.getMessageContent();
        final Object other$messageContent = other.getMessageContent();
        if (this$messageContent == null ? other$messageContent != null : !this$messageContent.equals(other$messageContent))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CreateChatMessageRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $conversationId = this.getConversationId();
        result = result * PRIME + ($conversationId == null ? 43 : $conversationId.hashCode());
        final Object $messageContent = this.getMessageContent();
        result = result * PRIME + ($messageContent == null ? 43 : $messageContent.hashCode());
        return result;
    }

    public String toString() {
        return "CreateChatMessageRequest(conversationId=" + this.getConversationId() + ", messageContent=" + this.getMessageContent() + ")";
    }

    public static class CreateChatMessageRequestBuilder {
        private @NotBlank String conversationId;
        private @NotBlank String messageContent;

        CreateChatMessageRequestBuilder() {
        }

        public CreateChatMessageRequest.CreateChatMessageRequestBuilder conversationId(@NotBlank String conversationId) {
            this.conversationId = conversationId;
            return this;
        }

        public CreateChatMessageRequest.CreateChatMessageRequestBuilder messageContent(@NotBlank String messageContent) {
            this.messageContent = messageContent;
            return this;
        }

        public CreateChatMessageRequest build() {
            return new CreateChatMessageRequest(conversationId, messageContent);
        }

        public String toString() {
            return "CreateChatMessageRequest.CreateChatMessageRequestBuilder(conversationId=" + this.conversationId + ", messageContent=" + this.messageContent + ")";
        }
    }
}
