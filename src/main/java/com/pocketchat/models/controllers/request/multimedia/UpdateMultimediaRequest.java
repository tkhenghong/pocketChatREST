package com.pocketchat.models.controllers.request.multimedia;

public class UpdateMultimediaRequest extends MultimediaRequest {
    UpdateMultimediaRequest(String id, String localFullFileUrl, String localThumbnailUrl, String remoteThumbnailUrl, String remoteFullFileUrl, String messageId, String userContactId, String conversationId, String userId, Integer fileSize) {
        super(id, localFullFileUrl, localThumbnailUrl, remoteThumbnailUrl, remoteFullFileUrl, messageId, userContactId, conversationId, userId, fileSize);
    }
}
