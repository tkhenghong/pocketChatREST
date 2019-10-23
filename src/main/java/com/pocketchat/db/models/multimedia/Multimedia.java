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

    // Don't have to upload local directory to server
    private String localFullFileUrl;

    private String localThumbnailUrl;

    // If Personal Conversation UserContact stranger has not set up photo yet
//    @NotBlank
    private String remoteThumbnailUrl;

//    @NotBlank
    private String remoteFullFileUrl;

    // Can be optionally linked to a Message
    private String messageId;
    // Can be optionally linked to a UserContact
    private String userContactId;
    // Can be optionally linked to a conversationGroup
    private String conversationId;

    private String userId;
}
