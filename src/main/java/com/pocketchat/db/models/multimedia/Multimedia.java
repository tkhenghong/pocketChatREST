package com.pocketchat.db.models.multimedia;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "multimedia")
public class Multimedia {

    // Image, Video, Gifs, Sticker, Recording, links
    @Id
    private String id;

    @NotBlank
    private String localFullFileUrl;

    @NotBlank
    private String localThumbnailUrl;

    @NotBlank
    private String remoteThumbnailUrl;

    @NotBlank
    private String remoteFullFileUrl;

    @NotBlank
    private String imageDataId;

    @NotBlank
    private String imageFileId;

    @NotBlank
    private String messageId;

    @NotBlank
    private String userContactId;

    @NotBlank
    private String conversationId;
}
