package com.pocketchat.services.kafka;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ProducerImpl implements Producer {
//
//    private static final Logger logger = LoggerFactory.getLogger(ProducerImpl.class);
//    private static final String TOPIC = "users";
//
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Autowired
//    ProducerImpl(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//
//    @Override
//    public void sendMessage(String message) {
//        logger.info(String.format("$$ -> Producing message --> %s", message));
//        this.kafkaTemplate.send(TOPIC, message);
//    }
//}
