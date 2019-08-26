package com.pocketchat.server.configurations.mongoDB;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class MongoDBConfiguration extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return "pocketChatDB";
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient("127.0.0.1", 27017);
    }

    // Note: We didn’t need to define MongoTemplate bean in the previous configuration as it’s already defined in AbstractMongoConfiguration
}
