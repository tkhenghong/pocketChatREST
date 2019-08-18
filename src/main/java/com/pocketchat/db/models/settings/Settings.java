package com.pocketchat.db.models.settings;

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
@Document(collection = "settings")
public class Settings {

    @Id
    private String id;

    @NotBlank
    private String userId;

    private boolean notification;
}
