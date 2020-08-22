package com.pocketchat.server.configurations.mongoDB;
// TODO: Replaced by application.properties configuration to be used in Docker
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//@Configuration
//public class MongoDBConfiguration extends AbstractMongoClientConfiguration {
//
//    @Value("${spring.data.mongodb.database}")
//    String databaseName;
//
//    @Override
//    public String getDatabaseName() {
//        return "pocketChatDB";
//    }
//
//    @Bean
//    public MongoClient mongoClient() {
//        return MongoClients.create("mongodb://localhost:27017");
//    }
//
//    @Bean
//    public MongoTemplate mongoTemplate() {
//        return new MongoTemplate(mongoClient(), "pocketChatDB");
//    }
//}
