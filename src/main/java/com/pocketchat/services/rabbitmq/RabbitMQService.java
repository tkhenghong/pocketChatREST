package com.pocketchat.services.rabbitmq;

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
