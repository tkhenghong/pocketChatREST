package com.pocketchat.models.multimedia;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode

@Document(collection = "multimedia")
public class Multimedia {

    // Image, Video, Gifs, Sticker, Recording, links
    @Id
    String id;

    @NotBlank
    String localFullFileUrl;

    @NotBlank
    String localThumbnailUrl;

    @NotBlank
    String remoteThumbnailUrl;

    @NotBlank
    String remoteFullFileUrl;

    @NotBlank
    String imageDataId;

    @NotBlank
    String imageFileId;

    @NotBlank
    String messageId;

    @NotBlank
    String userContactId;

    @NotBlank
    String conversationId;
}
