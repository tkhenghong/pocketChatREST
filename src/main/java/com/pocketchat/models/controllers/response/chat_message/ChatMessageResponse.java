package com.pocketchat.models.controllers.response.chat_message;

import com.pocketchat.models.enums.chat_message.ChatMessageStatus;

import java.time.LocalDateTime;

public class ChatMessageResponse {

    private String id;

    private String conversationId;

    private String senderId;

    private String senderName;

    private String senderMobileNo;

    private ChatMessageStatus chatMessageStatus;

    private String messageContent;

    private String multimediaId;

    private LocalDateTime createdTime;

    private LocalDateTime sentTime;

    ChatMessageResponse(String id, String conversationId, String senderId, String senderName, String senderMobileNo, ChatMessageStatus chatMessageStatus, String messageContent, String multimediaId, LocalDateTime createdTime, LocalDateTime sentTime) {
        this.id = id;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderMobileNo = senderMobileNo;
        this.chatMessageStatus = chatMessageStatus;
        this.messageContent = messageContent;
        this.multimediaId = multimediaId;
        this.createdTime = createdTime;
        this.sentTime = sentTime;
    }

    public static ChatMessageResponseBuilder builder() {
        return new ChatMessageResponseBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getConversationId() {
        return this.conversationId;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public String getSenderName() {
        return this.senderName;
    }

    public String getSenderMobileNo() {
        return this.senderMobileNo;
    }

    public ChatMessageStatus getChatMessageStatus() {
        return this.chatMessageStatus;
    }

    public String getMessageContent() {
        return this.messageContent;
    }

    public String getMultimediaId() {
        return this.multimediaId;
    }

    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }

    public LocalDateTime getSentTime() {
        return this.sentTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setSenderMobileNo(String senderMobileNo) {
        this.senderMobileNo = senderMobileNo;
    }

    public void setChatMessageStatus(ChatMessageStatus chatMessageStatus) {
        this.chatMessageStatus = chatMessageStatus;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public void setMultimediaId(String multimediaId) {
        this.multimediaId = multimediaId;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ChatMessageResponse)) return false;
        final ChatMessageResponse other = (ChatMessageResponse) o;
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
        final Object this$chatMessageStatus = this.getChatMessageStatus();
        final Object other$chatMessageStatus = other.getChatMessageStatus();
        if (this$chatMessageStatus == null ? other$chatMessageStatus != null : !this$chatMessageStatus.equals(other$chatMessageStatus))
            return false;
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
        return other instanceof ChatMessageResponse;
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
        final Object $chatMessageStatus = this.getChatMessageStatus();
        result = result * PRIME + ($chatMessageStatus == null ? 43 : $chatMessageStatus.hashCode());
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
        return "ChatMessageResponse(id=" + this.getId() + ", conversationId=" + this.getConversationId() + ", senderId=" + this.getSenderId() + ", senderName=" + this.getSenderName() + ", senderMobileNo=" + this.getSenderMobileNo() + ", chatMessageStatus=" + this.getChatMessageStatus() + ", messageContent=" + this.getMessageContent() + ", multimediaId=" + this.getMultimediaId() + ", createdTime=" + this.getCreatedTime() + ", sentTime=" + this.getSentTime() + ")";
    }

    public static class ChatMessageResponseBuilder {
        private String id;
        private String conversationId;
        private String senderId;
        private String senderName;
        private String senderMobileNo;
        private ChatMessageStatus chatMessageStatus;
        private String messageContent;
        private String multimediaId;
        private LocalDateTime createdTime;
        private LocalDateTime sentTime;

        ChatMessageResponseBuilder() {
        }

        public ChatMessageResponse.ChatMessageResponseBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ChatMessageResponse.ChatMessageResponseBuilder conversationId(String conversationId) {
            this.conversationId = conversationId;
            return this;
        }

        public ChatMessageResponse.ChatMessageResponseBuilder senderId(String senderId) {
            this.senderId = senderId;
            return this;
        }

        public ChatMessageResponse.ChatMessageResponseBuilder senderName(String senderName) {
            this.senderName = senderName;
            return this;
        }

        public ChatMessageResponse.ChatMessageResponseBuilder senderMobileNo(String senderMobileNo) {
            this.senderMobileNo = senderMobileNo;
            return this;
        }

        public ChatMessageResponse.ChatMessageResponseBuilder chatMessageStatus(ChatMessageStatus chatMessageStatus) {
            this.chatMessageStatus = chatMessageStatus;
            return this;
        }

        public ChatMessageResponse.ChatMessageResponseBuilder messageContent(String messageContent) {
            this.messageContent = messageContent;
            return this;
        }

        public ChatMessageResponse.ChatMessageResponseBuilder multimediaId(String multimediaId) {
            this.multimediaId = multimediaId;
            return this;
        }

        public ChatMessageResponse.ChatMessageResponseBuilder createdTime(LocalDateTime createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public ChatMessageResponse.ChatMessageResponseBuilder sentTime(LocalDateTime sentTime) {
            this.sentTime = sentTime;
            return this;
        }

        public ChatMessageResponse build() {
            return new ChatMessageResponse(id, conversationId, senderId, senderName, senderMobileNo, chatMessageStatus, messageContent, multimediaId, createdTime, sentTime);
        }

        public String toString() {
            return "ChatMessageResponse.ChatMessageResponseBuilder(id=" + this.id + ", conversationId=" + this.conversationId + ", senderId=" + this.senderId + ", senderName=" + this.senderName + ", senderMobileNo=" + this.senderMobileNo + ", chatMessageStatus=" + this.chatMessageStatus + ", messageContent=" + this.messageContent + ", multimediaId=" + this.multimediaId + ", createdTime=" + this.createdTime + ", sentTime=" + this.sentTime + ")";
        }
    }
}
