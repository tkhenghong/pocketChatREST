package com.pocketchat.services.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaServiceImpl implements KafkaService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    KafkaServiceImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * Add message into Kafka.
     *
     * @param id:      An identifier for the Kafka topic.
     * @param message: A String converted from object(typically WebSocketMessage object) to be store into Kafka.
     */
    @Override
    public void addMessage(String id, String message) {
        logger.info("Adding message into Kafka with ID: {}", id);
        // Find the user in Kafka topics.

        // Send messages into topics.
    }

    /**
     * Add message into Kafka topic based on @param id.
     *
     * @param id: An identifier used to find the relative topic in Kafka.
     * @return A list of messages from Kafka topic, typically can be converted into WebSocketMessage.
     */
    @Override
    public List<String> getMessages(String id) {
        logger.info("Get messages from Kafka using ID: {}", id);
        // Find the user in Kafka topics.

        // Get current not yet send messages.

        return new ArrayList<>();
    }
}
