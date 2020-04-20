package com.pocketchat.server.configurations.websocket;

public class CustomizedMessage {

    private String id;

    private String conversationId;

    private String senderId;

    private String senderName;

    private String senderMobileNo;

    private String receiverId;

    private String receiverName;

    private String receiverMobileNo;

    private String type;

    private String status; // Sent, received, unread, read

    private String messageContent;

    private String multimediaId;

    // Due to unable to change from JSON string to DateTime object directly
    private Long timestamp;

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

    public Long getTimestamp() {
        return this.timestamp;
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

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return "CustomizedMessage(id=" + this.getId() + ", conversationId=" + this.getConversationId() + ", senderId=" + this.getSenderId() + ", senderName=" + this.getSenderName() + ", senderMobileNo=" + this.getSenderMobileNo() + ", receiverId=" + this.getReceiverId() + ", receiverName=" + this.getReceiverName() + ", receiverMobileNo=" + this.getReceiverMobileNo() + ", type=" + this.getType() + ", status=" + this.getStatus() + ", messageContent=" + this.getMessageContent() + ", multimediaId=" + this.getMultimediaId() + ", timestamp=" + this.getTimestamp() + ")";
    }
}
