package com.pocketchat.db.models.chat_message;

import com.pocketchat.models.enums.chat_message.ChatMessageStatus;
import com.pocketchat.server.configurations.auditing.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document(collection = "chat_message")
public class ChatMessage extends Auditable {

    @Id
    private String id;

    @NotBlank
    private String conversationId;

    @NotBlank
    private String senderId;

    @NotBlank
    private String senderName;

    @NotBlank
    private String senderMobileNo;

    private ChatMessageStatus chatMessageStatus;

    @NotBlank
    private String messageContent;

    private String multimediaId;

    ChatMessage(String id, @NotBlank String conversationId, @NotBlank String senderId, @NotBlank String senderName, @NotBlank String senderMobileNo, ChatMessageStatus chatMessageStatus, @NotBlank String messageContent, String multimediaId) {
        this.id = id;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderMobileNo = senderMobileNo;
        this.chatMessageStatus = chatMessageStatus;
        this.messageContent = messageContent;
        this.multimediaId = multimediaId;
    }

    public static ChatMessageBuilder builder() {
        return new ChatMessageBuilder();
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

    public ChatMessageStatus getChatMessageStatus() {
        return this.chatMessageStatus;
    }

    public @NotBlank String getMessageContent() {
        return this.messageContent;
    }

    public String getMultimediaId() {
        return this.multimediaId;
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

    public void setChatMessageStatus(ChatMessageStatus chatMessageStatus) {
        this.chatMessageStatus = chatMessageStatus;
    }

    public void setMessageContent(@NotBlank String messageContent) {
        this.messageContent = messageContent;
    }

    public void setMultimediaId(String multimediaId) {
        this.multimediaId = multimediaId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ChatMessage)) return false;
        final ChatMessage other = (ChatMessage) o;
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
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ChatMessage;
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
        return result;
    }

    public String toString() {
        return "ChatMessage(id=" + this.getId() + ", conversationId=" + this.getConversationId() + ", senderId=" + this.getSenderId() + ", senderName=" + this.getSenderName() + ", senderMobileNo=" + this.getSenderMobileNo() + ", chatMessageStatus=" + this.getChatMessageStatus() + ", messageContent=" + this.getMessageContent() + ", multimediaId=" + this.getMultimediaId() + ")";
    }

    public static class ChatMessageBuilder {
        private String id;
        private @NotBlank String conversationId;
        private @NotBlank String senderId;
        private @NotBlank String senderName;
        private @NotBlank String senderMobileNo;
        private ChatMessageStatus chatMessageStatus;
        private @NotBlank String messageContent;
        private String multimediaId;

        ChatMessageBuilder() {
        }

        public ChatMessage.ChatMessageBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ChatMessage.ChatMessageBuilder conversationId(@NotBlank String conversationId) {
            this.conversationId = conversationId;
            return this;
        }

        public ChatMessage.ChatMessageBuilder senderId(@NotBlank String senderId) {
            this.senderId = senderId;
            return this;
        }

        public ChatMessage.ChatMessageBuilder senderName(@NotBlank String senderName) {
            this.senderName = senderName;
            return this;
        }

        public ChatMessage.ChatMessageBuilder senderMobileNo(@NotBlank String senderMobileNo) {
            this.senderMobileNo = senderMobileNo;
            return this;
        }

        public ChatMessage.ChatMessageBuilder chatMessageStatus(ChatMessageStatus chatMessageStatus) {
            this.chatMessageStatus = chatMessageStatus;
            return this;
        }

        public ChatMessage.ChatMessageBuilder messageContent(@NotBlank String messageContent) {
            this.messageContent = messageContent;
            return this;
        }

        public ChatMessage.ChatMessageBuilder multimediaId(String multimediaId) {
            this.multimediaId = multimediaId;
            return this;
        }

        public ChatMessage build() {
            return new ChatMessage(id, conversationId, senderId, senderName, senderMobileNo, chatMessageStatus, messageContent, multimediaId);
        }

        public String toString() {
            return "ChatMessage.ChatMessageBuilder(id=" + this.id + ", conversationId=" + this.conversationId + ", senderId=" + this.senderId + ", senderName=" + this.senderName + ", senderMobileNo=" + this.senderMobileNo + ", chatMessageStatus=" + this.chatMessageStatus + ", messageContent=" + this.messageContent + ", multimediaId=" + this.multimediaId + ")";
        }
    }
}
