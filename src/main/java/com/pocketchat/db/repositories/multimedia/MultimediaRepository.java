package com.pocketchat.db.repositories.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MultimediaRepository extends MongoRepository<Multimedia, String> {
}
