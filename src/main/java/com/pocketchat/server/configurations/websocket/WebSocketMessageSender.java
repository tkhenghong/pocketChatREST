package com.pocketchat.server.configurations.websocket;

import com.pocketchat.services.kafka.KafkaService;
import com.pocketchat.services.rabbitmq.RabbitMQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Service
public class WebSocketMessageSender {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RabbitMQService rabbitMQService;

    private final KafkaService kafkaService;

    @Autowired
    WebSocketMessageSender(@Lazy RabbitMQService rabbitMQService, @Lazy KafkaService kafkaService) {
        this.rabbitMQService = rabbitMQService;
        this.kafkaService = kafkaService;
    }

    /**
     * TODO: Finish the logic here.
     * Send message by read messages from Kafka and RabbitMQ, then send those messages to WebSocketSessions.
     *
     * @param id:                   ID for getting Kafka topics/RabbitMQ queues. Typcially by using UserContact ID.
     * @param webSocketSessionList: List of WebSocketSessions. When the messages are found, all online WebSocketSessions will get the messages.
     */
    public void sendMessage(String id, List<WebSocketSession> webSocketSessionList) {
        logger.info("Sending messages to id: {}", id);
        // Find user in RabbitMQ
        logger.info("Found user queue to RabbitMQ");
        // Find user in Kafka
        logger.info("Found user queue to Kafka");
        // Send message to it.
        logger.info("Sending messages...");
    }
}
