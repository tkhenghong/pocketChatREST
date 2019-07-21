package com.pocketchat.models.settings;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode

@Document(collection = "settings")
public class Settings {

    @Id
    String id;

    @NotBlank
    String userId;

    @NotBlank
    boolean notification;
}
