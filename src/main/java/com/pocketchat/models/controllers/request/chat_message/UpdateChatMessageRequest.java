package com.pocketchat.models.controllers.request.chat_message;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class UpdateChatMessageRequest {

    private String id;

    @NotBlank
    private String conversationId;

    // Sender START TODO: Create these fields in the system. Not sent from the frontend.
    @NotBlank
    private String senderId;

    @NotBlank
    private String senderName;

    @NotBlank
    private String senderMobileNo;
    // Sender END

    private String type;

    private String status; // Sent, received, unread, read

    @NotBlank
    private String messageContent;

    private String multimediaId;

    @NotNull
    private LocalDateTime createdTime;

    private LocalDateTime sentTime;

    UpdateChatMessageRequest(String id, @NotBlank String conversationId, @NotBlank String senderId, @NotBlank String senderName, @NotBlank String senderMobileNo, String type, String status, @NotBlank String messageContent, String multimediaId, @NotNull LocalDateTime createdTime, LocalDateTime sentTime) {
        this.id = id;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderMobileNo = senderMobileNo;
        this.type = type;
        this.status = status;
        this.messageContent = messageContent;
        this.multimediaId = multimediaId;
        this.createdTime = createdTime;
        this.sentTime = sentTime;
    }

    public static UpdateChatMessageRequestBuilder builder() {
        return new UpdateChatMessageRequestBuilder();
    }


    public String getId() {
        return this.id;
    }

    public @NotBlank String getConversationId() {
        return this.conversationId;
    }

    public @NotBlank String getSenderId() {
        return this.senderId;
    }

    public @NotBlank String getSenderName() {
        return this.senderName;
    }

    public @NotBlank String getSenderMobileNo() {
        return this.senderMobileNo;
    }

    public String getType() {
        return this.type;
    }

    public String getStatus() {
        return this.status;
    }

    public @NotBlank String getMessageContent() {
        return this.messageContent;
    }

    public String getMultimediaId() {
        return this.multimediaId;
    }

    public @NotNull LocalDateTime getCreatedTime() {
        return this.createdTime;
    }

    public LocalDateTime getSentTime() {
        return this.sentTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setConversationId(@NotBlank String conversationId) {
        this.conversationId = conversationId;
    }

    public void setSenderId(@NotBlank String senderId) {
        this.senderId = senderId;
    }

    public void setSenderName(@NotBlank String senderName) {
        this.senderName = senderName;
    }

    public void setSenderMobileNo(@NotBlank String senderMobileNo) {
        this.senderMobileNo = senderMobileNo;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessageContent(@NotBlank String messageContent) {
        this.messageContent = messageContent;
    }

    public void setMultimediaId(String multimediaId) {
        this.multimediaId = multimediaId;
    }

    public void setCreatedTime(@NotNull LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UpdateChatMessageRequest))
            return false;
        final UpdateChatMessageRequest other = (UpdateChatMessageRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$conversationId = this.getConversationId();
        final Object other$conversationId = other.getConversationId();
        if (this$conversationId == null ? other$conversationId != null : !this$conversationId.equals(other$conversationId))
            return false;
        final Object this$senderId = this.getSenderId();
        final Object other$senderId = other.getSenderId();
        if (this$senderId == null ? other$senderId != null : !this$senderId.equals(other$senderId)) return false;
        final Object this$senderName = this.getSenderName();
        final Object other$senderName = other.getSenderName();
        if (this$senderName == null ? other$senderName != null : !this$senderName.equals(other$senderName))
            return false;
        final Object this$senderMobileNo = this.getSenderMobileNo();
        final Object other$senderMobileNo = other.getSenderMobileNo();
        if (this$senderMobileNo == null ? other$senderMobileNo != null : !this$senderMobileNo.equals(other$senderMobileNo))
            return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$messageContent = this.getMessageContent();
        final Object other$messageContent = other.getMessageContent();
        if (this$messageContent == null ? other$messageContent != null : !this$messageContent.equals(other$messageContent))
            return false;
        final Object this$multimediaId = this.getMultimediaId();
        final Object other$multimediaId = other.getMultimediaId();
        if (this$multimediaId == null ? other$multimediaId != null : !this$multimediaId.equals(other$multimediaId))
            return false;
        final Object this$createdTime = this.getCreatedTime();
        final Object other$createdTime = other.getCreatedTime();
        if (this$createdTime == null ? other$createdTime != null : !this$createdTime.equals(other$createdTime))
            return false;
        final Object this$sentTime = this.getSentTime();
        final Object other$sentTime = other.getSentTime();
        if (this$sentTime == null ? other$sentTime != null : !this$sentTime.equals(other$sentTime)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UpdateChatMessageRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $conversationId = this.getConversationId();
        result = result * PRIME + ($conversationId == null ? 43 : $conversationId.hashCode());
        final Object $senderId = this.getSenderId();
        result = result * PRIME + ($senderId == null ? 43 : $senderId.hashCode());
        final Object $senderName = this.getSenderName();
        result = result * PRIME + ($senderName == null ? 43 : $senderName.hashCode());
        final Object $senderMobileNo = this.getSenderMobileNo();
        result = result * PRIME + ($senderMobileNo == null ? 43 : $senderMobileNo.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $messageContent = this.getMessageContent();
        result = result * PRIME + ($messageContent == null ? 43 : $messageContent.hashCode());
        final Object $multimediaId = this.getMultimediaId();
        result = result * PRIME + ($multimediaId == null ? 43 : $multimediaId.hashCode());
        final Object $createdTime = this.getCreatedTime();
        result = result * PRIME + ($createdTime == null ? 43 : $createdTime.hashCode());
        final Object $sentTime = this.getSentTime();
        result = result * PRIME + ($sentTime == null ? 43 : $sentTime.hashCode());
        return result;
    }

    public String toString() {
        return "UpdateChatMessageRequest(id=" + this.getId() + ", conversationId=" + this.getConversationId() + ", senderId=" + this.getSenderId() + ", senderName=" + this.getSenderName() + ", senderMobileNo=" + this.getSenderMobileNo() + ", type=" + this.getType() + ", status=" + this.getStatus() + ", messageContent=" + this.getMessageContent() + ", multimediaId=" + this.getMultimediaId() + ", createdTime=" + this.getCreatedTime() + ", sentTime=" + this.getSentTime() + ")";
    }

    public static class UpdateChatMessageRequestBuilder {
        private String id;
        private @NotBlank String conversationId;
        private @NotBlank String senderId;
        private @NotBlank String senderName;
        private @NotBlank String senderMobileNo;
        private String type;
        private String status;
        private @NotBlank String messageContent;
        private String multimediaId;
        private @NotNull LocalDateTime createdTime;
        private LocalDateTime sentTime;

        UpdateChatMessageRequestBuilder() {
        }

        public UpdateChatMessageRequest.UpdateChatMessageRequestBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UpdateChatMessageRequest.UpdateChatMessageRequestBuilder conversationId(@NotBlank String conversationId) {
            this.conversationId = conversationId;
            return this;
        }

        public UpdateChatMessageRequest.UpdateChatMessageRequestBuilder senderId(@NotBlank String senderId) {
            this.senderId = senderId;
            return this;
        }

        public UpdateChatMessageRequest.UpdateChatMessageRequestBuilder senderName(@NotBlank String senderName) {
            this.senderName = senderName;
            return this;
        }

        public UpdateChatMessageRequest.UpdateChatMessageRequestBuilder senderMobileNo(@NotBlank String senderMobileNo) {
            this.senderMobileNo = senderMobileNo;
            return this;
        }

        public UpdateChatMessageRequest.UpdateChatMessageRequestBuilder type(String type) {
            this.type = type;
            return this;
        }

        public UpdateChatMessageRequest.UpdateChatMessageRequestBuilder status(String status) {
            this.status = status;
            return this;
        }

        public UpdateChatMessageRequest.UpdateChatMessageRequestBuilder messageContent(@NotBlank String messageContent) {
            this.messageContent = messageContent;
            return this;
        }

        public UpdateChatMessageRequest.UpdateChatMessageRequestBuilder multimediaId(String multimediaId) {
            this.multimediaId = multimediaId;
            return this;
        }

        public UpdateChatMessageRequest.UpdateChatMessageRequestBuilder createdTime(@NotNull LocalDateTime createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public UpdateChatMessageRequest.UpdateChatMessageRequestBuilder sentTime(LocalDateTime sentTime) {
            this.sentTime = sentTime;
            return this;
        }

        public UpdateChatMessageRequest build() {
            return new UpdateChatMessageRequest(id, conversationId, senderId, senderName, senderMobileNo, type, status, messageContent, multimediaId, createdTime, sentTime);
        }

        public String toString() {
            return "UpdateChatMessageRequest.UpdateChatMessageRequestBuilder(id=" + this.id + ", conversationId=" + this.conversationId + ", senderId=" + this.senderId + ", senderName=" + this.senderName + ", senderMobileNo=" + this.senderMobileNo + ", type=" + this.type + ", status=" + this.status + ", messageContent=" + this.messageContent + ", multimediaId=" + this.multimediaId + ", createdTime=" + this.createdTime + ", sentTime=" + this.sentTime + ")";
        }
    }
}
