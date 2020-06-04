package com.pocketchat.services.rabbitmq;

import com.pocketchat.db.models.chat_message.ChatMessage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public interface RabbitMQService {
    void addMessageToQueue(String queueName, String message) throws InterruptedException;

    void listenMessagesFromQueue(String queueName) throws IOException, TimeoutException;
}
