package com.pocketchat.services.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.Connection;

public interface RabbitMQService {

    Connection createConnection();

    Channel getChannel(Connection connection);

    Channel getChannel();

    void addMessageToQueue(String queueName, String exchangeName, String routingKey, String message);

    void listenMessagesFromQueue(String queueName, String consumerTag);

    Boolean closeChannelAndConnection(Connection connection, Channel channel, String consumerTag);
}
