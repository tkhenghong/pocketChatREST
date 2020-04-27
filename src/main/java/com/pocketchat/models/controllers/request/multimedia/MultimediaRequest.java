package com.pocketchat.models.controllers.request.multimedia;

import javax.validation.constraints.NotBlank;

public class MultimediaRequest {
    private String id;

    private String localFullFileUrl;

    private String localThumbnailUrl;

    @NotBlank
    private String remoteThumbnailUrl;

    @NotBlank
    private String remoteFullFileUrl;

    private String messageId;
    private String userContactId;
    private String conversationId;

    private String userId;

    private Integer fileSize;

    MultimediaRequest(String id, String localFullFileUrl, String localThumbnailUrl, String remoteThumbnailUrl, String remoteFullFileUrl, String messageId, String userContactId, String conversationId, String userId, Integer fileSize) {
        this.id = id;
        this.localFullFileUrl = localFullFileUrl;
        this.localThumbnailUrl = localThumbnailUrl;
        this.remoteThumbnailUrl = remoteThumbnailUrl;
        this.remoteFullFileUrl = remoteFullFileUrl;
        this.messageId = messageId;
        this.userContactId = userContactId;
        this.conversationId = conversationId;
        this.userId = userId;
        this.fileSize = fileSize;
    }

    public static MultimediaRequestBuilder builder() {
        return new MultimediaRequestBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getLocalFullFileUrl() {
        return this.localFullFileUrl;
    }

    public String getLocalThumbnailUrl() {
        return this.localThumbnailUrl;
    }

    public String getRemoteThumbnailUrl() {
        return this.remoteThumbnailUrl;
    }

    public String getRemoteFullFileUrl() {
        return this.remoteFullFileUrl;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public String getUserContactId() {
        return this.userContactId;
    }

    public String getConversationId() {
        return this.conversationId;
    }

    public String getUserId() {
        return this.userId;
    }

    public Integer getFileSize() {
        return this.fileSize;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocalFullFileUrl(String localFullFileUrl) {
        this.localFullFileUrl = localFullFileUrl;
    }

    public void setLocalThumbnailUrl(String localThumbnailUrl) {
        this.localThumbnailUrl = localThumbnailUrl;
    }

    public void setRemoteThumbnailUrl(String remoteThumbnailUrl) {
        this.remoteThumbnailUrl = remoteThumbnailUrl;
    }

    public void setRemoteFullFileUrl(String remoteFullFileUrl) {
        this.remoteFullFileUrl = remoteFullFileUrl;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setUserContactId(String userContactId) {
        this.userContactId = userContactId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MultimediaRequest)) return false;
        final MultimediaRequest other = (MultimediaRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$localFullFileUrl = this.getLocalFullFileUrl();
        final Object other$localFullFileUrl = other.getLocalFullFileUrl();
        if (this$localFullFileUrl == null ? other$localFullFileUrl != null : !this$localFullFileUrl.equals(other$localFullFileUrl))
            return false;
        final Object this$localThumbnailUrl = this.getLocalThumbnailUrl();
        final Object other$localThumbnailUrl = other.getLocalThumbnailUrl();
        if (this$localThumbnailUrl == null ? other$localThumbnailUrl != null : !this$localThumbnailUrl.equals(other$localThumbnailUrl))
            return false;
        final Object this$remoteThumbnailUrl = this.getRemoteThumbnailUrl();
        final Object other$remoteThumbnailUrl = other.getRemoteThumbnailUrl();
        if (this$remoteThumbnailUrl == null ? other$remoteThumbnailUrl != null : !this$remoteThumbnailUrl.equals(other$remoteThumbnailUrl))
            return false;
        final Object this$remoteFullFileUrl = this.getRemoteFullFileUrl();
        final Object other$remoteFullFileUrl = other.getRemoteFullFileUrl();
        if (this$remoteFullFileUrl == null ? other$remoteFullFileUrl != null : !this$remoteFullFileUrl.equals(other$remoteFullFileUrl))
            return false;
        final Object this$messageId = this.getMessageId();
        final Object other$messageId = other.getMessageId();
        if (this$messageId == null ? other$messageId != null : !this$messageId.equals(other$messageId)) return false;
        final Object this$userContactId = this.getUserContactId();
        final Object other$userContactId = other.getUserContactId();
        if (this$userContactId == null ? other$userContactId != null : !this$userContactId.equals(other$userContactId))
            return false;
        final Object this$conversationId = this.getConversationId();
        final Object other$conversationId = other.getConversationId();
        if (this$conversationId == null ? other$conversationId != null : !this$conversationId.equals(other$conversationId))
            return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$fileSize = this.getFileSize();
        final Object other$fileSize = other.getFileSize();
        if (this$fileSize == null ? other$fileSize != null : !this$fileSize.equals(other$fileSize)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MultimediaRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $localFullFileUrl = this.getLocalFullFileUrl();
        result = result * PRIME + ($localFullFileUrl == null ? 43 : $localFullFileUrl.hashCode());
        final Object $localThumbnailUrl = this.getLocalThumbnailUrl();
        result = result * PRIME + ($localThumbnailUrl == null ? 43 : $localThumbnailUrl.hashCode());
        final Object $remoteThumbnailUrl = this.getRemoteThumbnailUrl();
        result = result * PRIME + ($remoteThumbnailUrl == null ? 43 : $remoteThumbnailUrl.hashCode());
        final Object $remoteFullFileUrl = this.getRemoteFullFileUrl();
        result = result * PRIME + ($remoteFullFileUrl == null ? 43 : $remoteFullFileUrl.hashCode());
        final Object $messageId = this.getMessageId();
        result = result * PRIME + ($messageId == null ? 43 : $messageId.hashCode());
        final Object $userContactId = this.getUserContactId();
        result = result * PRIME + ($userContactId == null ? 43 : $userContactId.hashCode());
        final Object $conversationId = this.getConversationId();
        result = result * PRIME + ($conversationId == null ? 43 : $conversationId.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $fileSize = this.getFileSize();
        result = result * PRIME + ($fileSize == null ? 43 : $fileSize.hashCode());
        return result;
    }

    public String toString() {
        return "MultimediaRequest(id=" + this.getId() + ", localFullFileUrl=" + this.getLocalFullFileUrl() + ", localThumbnailUrl=" + this.getLocalThumbnailUrl() + ", remoteThumbnailUrl=" + this.getRemoteThumbnailUrl() + ", remoteFullFileUrl=" + this.getRemoteFullFileUrl() + ", messageId=" + this.getMessageId() + ", userContactId=" + this.getUserContactId() + ", conversationId=" + this.getConversationId() + ", userId=" + this.getUserId() + ", fileSize=" + this.getFileSize() + ")";
    }

    public static class MultimediaRequestBuilder {
        private String id;
        private String localFullFileUrl;
        private String localThumbnailUrl;
        private String remoteThumbnailUrl;
        private String remoteFullFileUrl;
        private String messageId;
        private String userContactId;
        private String conversationId;
        private String userId;
        private Integer fileSize;

        MultimediaRequestBuilder() {
        }

        public MultimediaRequest.MultimediaRequestBuilder id(String id) {
            this.id = id;
            return this;
        }

        public MultimediaRequest.MultimediaRequestBuilder localFullFileUrl(String localFullFileUrl) {
            this.localFullFileUrl = localFullFileUrl;
            return this;
        }

        public MultimediaRequest.MultimediaRequestBuilder localThumbnailUrl(String localThumbnailUrl) {
            this.localThumbnailUrl = localThumbnailUrl;
            return this;
        }

        public MultimediaRequest.MultimediaRequestBuilder remoteThumbnailUrl(String remoteThumbnailUrl) {
            this.remoteThumbnailUrl = remoteThumbnailUrl;
            return this;
        }

        public MultimediaRequest.MultimediaRequestBuilder remoteFullFileUrl(String remoteFullFileUrl) {
            this.remoteFullFileUrl = remoteFullFileUrl;
            return this;
        }

        public MultimediaRequest.MultimediaRequestBuilder messageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public MultimediaRequest.MultimediaRequestBuilder userContactId(String userContactId) {
            this.userContactId = userContactId;
            return this;
        }

        public MultimediaRequest.MultimediaRequestBuilder conversationId(String conversationId) {
            this.conversationId = conversationId;
            return this;
        }

        public MultimediaRequest.MultimediaRequestBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public MultimediaRequest.MultimediaRequestBuilder fileSize(Integer fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public MultimediaRequest build() {
            return new MultimediaRequest(id, localFullFileUrl, localThumbnailUrl, remoteThumbnailUrl, remoteFullFileUrl, messageId, userContactId, conversationId, userId, fileSize);
        }

        public String toString() {
            return "MultimediaRequest.MultimediaRequestBuilder(id=" + this.id + ", localFullFileUrl=" + this.localFullFileUrl + ", localThumbnailUrl=" + this.localThumbnailUrl + ", remoteThumbnailUrl=" + this.remoteThumbnailUrl + ", remoteFullFileUrl=" + this.remoteFullFileUrl + ", messageId=" + this.messageId + ", userContactId=" + this.userContactId + ", conversationId=" + this.conversationId + ", userId=" + this.userId + ", fileSize=" + this.fileSize + ")";
        }
    }
}