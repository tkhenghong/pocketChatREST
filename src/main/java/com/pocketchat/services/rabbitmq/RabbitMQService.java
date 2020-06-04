package com.pocketchat.services.rabbitmq;

import com.pocketchat.db.models.chat_message.ChatMessage;

import java.util.List;

public interface RabbitMQService {
    void addMessageToQueue(String queueName, String message) throws InterruptedException;

    void listenMessagesFromQueue(String queueName);
}
