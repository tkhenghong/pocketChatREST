package com.pocketchat.services.kafka;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.stereotype.Service;

//@Service
//public class ConsumerImpl implements Consumer {
//
//    private final Logger logger = LoggerFactory.getLogger(Consumer.class);
//
//    @Override
//    @KafkaListener(topics = "test")
//    public void consume(String message,
//                        @Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) Integer key,
//                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
//                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//                        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
//
//        logger.info(String.format("$$ -> Consumed Message -> %s", message));
//    }
//}
