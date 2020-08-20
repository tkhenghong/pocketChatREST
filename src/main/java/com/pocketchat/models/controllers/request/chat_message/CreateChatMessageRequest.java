package com.pocketchat.models.controllers.request.chat_message;

import com.pocketchat.models.enums.chat_message.ChatMessageType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateChatMessageRequest {
    @NotBlank
    private String conversationId;

    @NotNull
    private ChatMessageType chatMessageType;

    @NotBlank
    private String messageContent;

    private String multimediaId;

    CreateChatMessageRequest(@NotBlank String conversationId, @NotNull ChatMessageType chatMessageType, @NotBlank String messageContent, String multimediaId) {
        this.conversationId = conversationId;
        this.chatMessageType = chatMessageType;
        this.messageContent = messageContent;
        this.multimediaId = multimediaId;
    }

    public static CreateChatMessageRequestBuilder builder() {
        return new CreateChatMessageRequestBuilder();
    }

    public @NotBlank String getConversationId() {
        return this.conversationId;
    }

    public @NotNull ChatMessageType getChatMessageType() {
        return this.chatMessageType;
    }

    public @NotBlank String getMessageContent() {
        return this.messageContent;
    }

    public String getMultimediaId() {
        return this.multimediaId;
    }

    public void setConversationId(@NotBlank String conversationId) {
        this.conversationId = conversationId;
    }

    public void setChatMessageType(@NotNull ChatMessageType chatMessageType) {
        this.chatMessageType = chatMessageType;
    }

    public void setMessageContent(@NotBlank String messageContent) {
        this.messageContent = messageContent;
    }

    public void setMultimediaId(String multimediaId) {
        this.multimediaId = multimediaId;
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
        final Object this$chatMessageType = this.getChatMessageType();
        final Object other$chatMessageType = other.getChatMessageType();
        if (this$chatMessageType == null ? other$chatMessageType != null : !this$chatMessageType.equals(other$chatMessageType))
            return false;
        final Object this$messageContent = this.getMessageContent();
        final Object other$messageContent = other.getMessageContent();
        if (this$messageContent == null ? other$messageContent != null : !this$messageContent.equals(other$messageContent))
            return false;
        final Object this$multimediaId = this.getMultimediaId();
        final Object other$multimediaId = other.getMultimediaId();
        if (this$multimediaId == null ? other$multimediaId != null : !this$multimediaId.equals(other$multimediaId))
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
        final Object $chatMessageType = this.getChatMessageType();
        result = result * PRIME + ($chatMessageType == null ? 43 : $chatMessageType.hashCode());
        final Object $messageContent = this.getMessageContent();
        result = result * PRIME + ($messageContent == null ? 43 : $messageContent.hashCode());
        final Object $multimediaId = this.getMultimediaId();
        result = result * PRIME + ($multimediaId == null ? 43 : $multimediaId.hashCode());
        return result;
    }

    public String toString() {
        return "CreateChatMessageRequest(conversationId=" + this.getConversationId() + ", chatMessageType=" + this.getChatMessageType() + ", messageContent=" + this.getMessageContent() + ", multimediaId=" + this.getMultimediaId() + ")";
    }

    public static class CreateChatMessageRequestBuilder {
        private @NotBlank String conversationId;
        private @NotNull ChatMessageType chatMessageType;
        private @NotBlank String messageContent;
        private String multimediaId;

        CreateChatMessageRequestBuilder() {
        }

        public CreateChatMessageRequest.CreateChatMessageRequestBuilder conversationId(@NotBlank String conversationId) {
            this.conversationId = conversationId;
            return this;
        }

        public CreateChatMessageRequest.CreateChatMessageRequestBuilder chatMessageType(@NotNull ChatMessageType chatMessageType) {
            this.chatMessageType = chatMessageType;
            return this;
        }

        public CreateChatMessageRequest.CreateChatMessageRequestBuilder messageContent(@NotBlank String messageContent) {
            this.messageContent = messageContent;
            return this;
        }

        public CreateChatMessageRequest.CreateChatMessageRequestBuilder multimediaId(String multimediaId) {
            this.multimediaId = multimediaId;
            return this;
        }

        public CreateChatMessageRequest build() {
            return new CreateChatMessageRequest(conversationId, chatMessageType, messageContent, multimediaId);
        }

        public String toString() {
            return "CreateChatMessageRequest.CreateChatMessageRequestBuilder(conversationId=" + this.conversationId + ", chatMessageType=" + this.chatMessageType + ", messageContent=" + this.messageContent + ", multimediaId=" + this.multimediaId + ")";
        }
    }
}
