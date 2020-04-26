package com.pocketchat.models.controllers.response.message;

import org.joda.time.DateTime;

public class MessageResponse {

    private String id;

    private String conversationId;

    private String senderId;

    private String senderName;

    private String senderMobileNo;

    private String receiverId;

    private String receiverName;

    private String receiverMobileNo;

    private String type;

    private String status;

    private String messageContent;

    private String multimediaId;

    private DateTime createdTime;

    private DateTime sentTime;

    MessageResponse(String id, String conversationId, String senderId, String senderName, String senderMobileNo, String receiverId, String receiverName, String receiverMobileNo, String type, String status, String messageContent, String multimediaId, DateTime createdTime, DateTime sentTime) {
        this.id = id;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderMobileNo = senderMobileNo;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.receiverMobileNo = receiverMobileNo;
        this.type = type;
        this.status = status;
        this.messageContent = messageContent;
        this.multimediaId = multimediaId;
        this.createdTime = createdTime;
        this.sentTime = sentTime;
    }

    public static MessageResponseBuilder builder() {
        return new MessageResponseBuilder();
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

    public String getReceiverId() {
        return this.receiverId;
    }

    public String getReceiverName() {
        return this.receiverName;
    }

    public String getReceiverMobileNo() {
        return this.receiverMobileNo;
    }

    public String getType() {
        return this.type;
    }

    public String getStatus() {
        return this.status;
    }

    public String getMessageContent() {
        return this.messageContent;
    }

    public String getMultimediaId() {
        return this.multimediaId;
    }

    public DateTime getCreatedTime() {
        return this.createdTime;
    }

    public DateTime getSentTime() {
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

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setReceiverMobileNo(String receiverMobileNo) {
        this.receiverMobileNo = receiverMobileNo;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public void setMultimediaId(String multimediaId) {
        this.multimediaId = multimediaId;
    }

    public void setCreatedTime(DateTime createdTime) {
        this.createdTime = createdTime;
    }

    public void setSentTime(DateTime sentTime) {
        this.sentTime = sentTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MessageResponse)) return false;
        final MessageResponse other = (MessageResponse) o;
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
        final Object this$receiverId = this.getReceiverId();
        final Object other$receiverId = other.getReceiverId();
        if (this$receiverId == null ? other$receiverId != null : !this$receiverId.equals(other$receiverId))
            return false;
        final Object this$receiverName = this.getReceiverName();
        final Object other$receiverName = other.getReceiverName();
        if (this$receiverName == null ? other$receiverName != null : !this$receiverName.equals(other$receiverName))
            return false;
        final Object this$receiverMobileNo = this.getReceiverMobileNo();
        final Object other$receiverMobileNo = other.getReceiverMobileNo();
        if (this$receiverMobileNo == null ? other$receiverMobileNo != null : !this$receiverMobileNo.equals(other$receiverMobileNo))
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
        return other instanceof MessageResponse;
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
        final Object $receiverId = this.getReceiverId();
        result = result * PRIME + ($receiverId == null ? 43 : $receiverId.hashCode());
        final Object $receiverName = this.getReceiverName();
        result = result * PRIME + ($receiverName == null ? 43 : $receiverName.hashCode());
        final Object $receiverMobileNo = this.getReceiverMobileNo();
        result = result * PRIME + ($receiverMobileNo == null ? 43 : $receiverMobileNo.hashCode());
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
        return "MessageResponse(id=" + this.getId() + ", conversationId=" + this.getConversationId() + ", senderId=" + this.getSenderId() + ", senderName=" + this.getSenderName() + ", senderMobileNo=" + this.getSenderMobileNo() + ", receiverId=" + this.getReceiverId() + ", receiverName=" + this.getReceiverName() + ", receiverMobileNo=" + this.getReceiverMobileNo() + ", type=" + this.getType() + ", status=" + this.getStatus() + ", messageContent=" + this.getMessageContent() + ", multimediaId=" + this.getMultimediaId() + ", createdTime=" + this.getCreatedTime() + ", sentTime=" + this.getSentTime() + ")";
    }

    public static class MessageResponseBuilder {
        private String id;
        private String conversationId;
        private String senderId;
        private String senderName;
        private String senderMobileNo;
        private String receiverId;
        private String receiverName;
        private String receiverMobileNo;
        private String type;
        private String status;
        private String messageContent;
        private String multimediaId;
        private DateTime createdTime;
        private DateTime sentTime;

        MessageResponseBuilder() {
        }

        public MessageResponse.MessageResponseBuilder id(String id) {
            this.id = id;
            return this;
        }

        public MessageResponse.MessageResponseBuilder conversationId(String conversationId) {
            this.conversationId = conversationId;
            return this;
        }

        public MessageResponse.MessageResponseBuilder senderId(String senderId) {
            this.senderId = senderId;
            return this;
        }

        public MessageResponse.MessageResponseBuilder senderName(String senderName) {
            this.senderName = senderName;
            return this;
        }

        public MessageResponse.MessageResponseBuilder senderMobileNo(String senderMobileNo) {
            this.senderMobileNo = senderMobileNo;
            return this;
        }

        public MessageResponse.MessageResponseBuilder receiverId(String receiverId) {
            this.receiverId = receiverId;
            return this;
        }

        public MessageResponse.MessageResponseBuilder receiverName(String receiverName) {
            this.receiverName = receiverName;
            return this;
        }

        public MessageResponse.MessageResponseBuilder receiverMobileNo(String receiverMobileNo) {
            this.receiverMobileNo = receiverMobileNo;
            return this;
        }

        public MessageResponse.MessageResponseBuilder type(String type) {
            this.type = type;
            return this;
        }

        public MessageResponse.MessageResponseBuilder status(String status) {
            this.status = status;
            return this;
        }

        public MessageResponse.MessageResponseBuilder messageContent(String messageContent) {
            this.messageContent = messageContent;
            return this;
        }

        public MessageResponse.MessageResponseBuilder multimediaId(String multimediaId) {
            this.multimediaId = multimediaId;
            return this;
        }

        public MessageResponse.MessageResponseBuilder createdTime(DateTime createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public MessageResponse.MessageResponseBuilder sentTime(DateTime sentTime) {
            this.sentTime = sentTime;
            return this;
        }

        public MessageResponse build() {
            return new MessageResponse(id, conversationId, senderId, senderName, senderMobileNo, receiverId, receiverName, receiverMobileNo, type, status, messageContent, multimediaId, createdTime, sentTime);
        }

        public String toString() {
            return "MessageResponse.MessageResponseBuilder(id=" + this.id + ", conversationId=" + this.conversationId + ", senderId=" + this.senderId + ", senderName=" + this.senderName + ", senderMobileNo=" + this.senderMobileNo + ", receiverId=" + this.receiverId + ", receiverName=" + this.receiverName + ", receiverMobileNo=" + this.receiverMobileNo + ", type=" + this.type + ", status=" + this.status + ", messageContent=" + this.messageContent + ", multimediaId=" + this.multimediaId + ", createdTime=" + this.createdTime + ", sentTime=" + this.sentTime + ")";
        }
    }
}
