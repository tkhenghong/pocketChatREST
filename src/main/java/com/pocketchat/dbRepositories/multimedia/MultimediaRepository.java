package com.pocketchat.dbRepositories.multimedia;

import com.pocketchat.models.multimedia.Multimedia;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MultimediaRepository extends MongoRepository<Multimedia, String> {
}
