package com.pocketchat.services.rabbitmq;

import com.pocketchat.db.models.chat_message.ChatMessage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public interface RabbitMQService {

    void declareExchange(String exchangeName);

    void declareQueue(String queueName);
    
    void declareBinding(String exchangeName, String queueName);

    void addMessageToQueue(String queueName, String exchangeName, String routingKey, String message);

    // userId = queueName
    // conversationGroupId = exchangeName
    // messageType = routingKey
    // deviceType = consumerTag
    void listenMessagesFromQueue(String queueName, String exchangeName, String routingKey, String consumerTag);
}
