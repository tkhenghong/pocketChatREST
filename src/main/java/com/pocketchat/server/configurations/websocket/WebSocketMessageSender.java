package com.pocketchat.server.configurations.websocket;

import com.pocketchat.services.kafka.KafkaService;
import com.pocketchat.services.rabbitmq.RabbitMQService;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class which is used to send messages from Kafka and RabbitMQ to the frontend client when they have connected to the server.
 */
@Service
public class WebSocketMessageSender {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RabbitMQService rabbitMQService;

    private final KafkaService kafkaService;

    private final WebSocketSessionManager webSocketSessionManager;

    @Autowired
    WebSocketMessageSender(@Lazy RabbitMQService rabbitMQService, @Lazy KafkaService kafkaService, WebSocketSessionManager webSocketSessionManager) {
        this.rabbitMQService = rabbitMQService;
        this.kafkaService = kafkaService;
        this.webSocketSessionManager = webSocketSessionManager;
    }

    /**
     * Send message by read messages from topic of Kafka and queue of RabbitMQ, then send those messages to WebSocketSessions.
     *
     * @param id:                   ID for getting Kafka topics/RabbitMQ queues. Typically by using UserContact ID.
     * @param webSocketSessionList: List of WebSocketSessions. When the messages are found, all online WebSocketSessions will get the messages.
     */
    public void sendMessage(String id, List<WebSocketSession> webSocketSessionList) {
        logger.info("Sending messages to ID: {}", id);
        handleRabbitMQQueueMessages(id, webSocketSessionList);
        handleKafkaTopicMessages(id, webSocketSessionList);
    }

    /**
     * Send object to current WebSocket sessions that linked to the userIds.
     * Mainly used to send messages to the target users that connected to the WebSocket currently.
     * @param payload: The message that has been converted to JSON string.
     * @param userIds: The IDs to be used to find the WebSocketSession objects.
     */
    public void sendMessageToWebSocketUsers(String payload, List<String> userIds) {
        List<WebSocketSession> webSocketSessionList = getWebSocketSessionsOfUserIds(userIds);
        webSocketSessionList.forEach(webSocketSession -> {
            if(webSocketSession.isOpen()) {
                TextMessage textMessage = new TextMessage(payload);
                try {
                    webSocketSession.sendMessage(textMessage);
                } catch (IOException ioException) {
                    logger.error("Unable to send message to WebSocket Session ID {}. Message: {}", webSocketSession.getId(), ioException.getMessage());
                    ioException.printStackTrace();
                }
            }
        });
    }

    /**
     * Get WebSocketSession objects related to the user IDs.
     * @param userIds: A list of User IDs(Typically ID of UserContact object).
     * @return A list of WebSocketSession object.
     */
    private List<WebSocketSession> getWebSocketSessionsOfUserIds(List<String> userIds) {
        List<WebSocketSession> webSocketSessionList = new ArrayList<>();
        userIds.forEach(userId -> webSocketSessionList.addAll(webSocketSessionManager.getWebSocketSessions(userId)));

        return webSocketSessionList;
    }

    /**
     * Create connection to the RabbitMQ, get messages and send them to WebSocket sessions.
     * TODO: Probably need to reference the link below:
     * TODO: https://stackoverflow.com/questions/47331469/easiest-way-to-construct-rabbitlistener-at-runtime/47332278
     * TODO: for simpler way of getting messages from RabbitMQ.
     *
     * @param queueName:         Name of the queue in RabbitMQ, typically ID of the UserContact object.
     * @param webSocketSessions: A list of WebSocket sessions. Maybe alive or dead.
     */
    private void handleRabbitMQQueueMessages(String queueName, List<WebSocketSession> webSocketSessions) {
        Connection connection = rabbitMQService.createConnection();
        Channel channel = rabbitMQService.getChannel(connection);

        try {
            AMQP.Queue.DeclareOk queueDeclareOkResponse = channel.queueDeclare(queueName, true, false, false, null);

            int remainingMessageCount = queueDeclareOkResponse.getMessageCount();

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    long deliveryTag = envelope.getDeliveryTag();

                    String message = new String(body);

                    logger.info("[routingKey: {}, contentType: {}, deliveryTag: {}, message: {}]", envelope.getRoutingKey(), properties.getContentType(), deliveryTag, message);

                    logger.info("Remaining message count from queue {}: {}", queueName, queueDeclareOkResponse.getMessageCount());

                    sendRabbitMQMessageToAllWebSocketSessions(channel, deliveryTag, message, webSocketSessions);

                    if (remainingMessageCount == deliveryTag) {
                        logger.info("Finish reading messages for {}.", queueName);
                        rabbitMQService.closeChannelAndConnection(connection, channel, consumerTag);
                    }
                }
            };

            channel.basicConsume(queueName, false, queueName, consumer);

            // If no messages at all
            if (remainingMessageCount == 0) {
                rabbitMQService.closeChannelAndConnection(connection, channel, queueName);
            }
        } catch (IOException exceptions) {
            exceptions.printStackTrace();
        }
    }

    /**
     * Send the message to all WebSocket sessions, so that whether the client is a web or mobile (when alive), they will get the messages.
     *
     * @param channel:           Channel which has the reference of the connected RabbitMQ.
     * @param deliveryTag:       Tag of the message in the RabbitMQ queue. Works like ID of the messages.
     * @param message:           Message. The content.
     * @param webSocketSessions: List of WebSocketSession objects.
     */
    void sendRabbitMQMessageToAllWebSocketSessions(Channel channel, long deliveryTag, String message, List<WebSocketSession> webSocketSessions) {
        webSocketSessions.forEach(webSocketSession -> {
            try {
                if (webSocketSession.isOpen()) {
                    TextMessage textMessage = new TextMessage(message);
                    webSocketSession.sendMessage(textMessage);

                    // Acknowledge the message is consumed
                    // TODO: What if frontend has error when compiling the messages?
                    channel.basicAck(deliveryTag, false);
                } else {
                    logger.warn("WebSocket Session ID {} is closed. Not sending message for this one.", webSocketSession.getId());
                }
            } catch (IOException ioException) {
                logger.error("Unable to send message to WebSocket Session ID {}. Message: {}", webSocketSession.getId(), ioException.getMessage());
                ioException.printStackTrace();
            }
        });
    }

    /**
     * Create connection to Kafka, get messages and send them to WebSocket sessions.
     *
     * @param queueName:         Name of the queue in RabbitMQ, typically ID of the UserContact object.
     * @param webSocketSessions: A list of WebSocket sessions. Maybe alive or dead.
     */
    private void handleKafkaTopicMessages(String queueName, List<WebSocketSession> webSocketSessions) {
        List<String> messages = kafkaService.getMessages(queueName, queueName); // Client ID and Kafka topic are both designed using User ID.

        // Send every message to every WebSocketSession.
        messages.forEach(message -> {
            webSocketSessions.forEach(webSocketSession -> {
                if (webSocketSession.isOpen()) {
                    WebSocketMessage webSocketMessage = new TextMessage(message);
                    try {
                        webSocketSession.sendMessage(webSocketMessage);
                    } catch (IOException e) {
                        logger.info("Unable to to this message to webSocket session ID: {}.", webSocketSession.getId());
                    }
                }
            });
        });
    }
}
