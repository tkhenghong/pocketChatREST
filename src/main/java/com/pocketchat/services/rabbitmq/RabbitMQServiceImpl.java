package com.pocketchat.services.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Service
public class RabbitMQServiceImpl implements RabbitMQService {

//    AmqpTemplate amqpTemplate;

    String exchangeName = "test-exchange-in-spring-boot-rabbitmq-test";
    String routingKey = "testing.routing.key";

    // Queue
    // Exchange
    // Binding
    // ConnectionFactory
    // AmqpTemplate
    // AmqpAdmin
    // SimpleMessageListenerContainer

//    @Autowired
//    RabbitMQServiceImpl(AmqpTemplate amqpTemplate) {
//        this.amqpTemplate = amqpTemplate;
//    }

    @Override
    public void addMessageToQueue(String queueName, String message) throws InterruptedException {
        AmqpTemplate amqpTemplate = amqpTemplate();
        amqpTemplate.convertAndSend(exchangeName, queueName, message);
    }

    /**
     * Listen a message from a queue,
     *
     * @param queueName: The name of the queue, usually user's ID
     */
    @Override
    public void listenMessagesFromQueue(String queueName) throws IOException, TimeoutException {
        SimpleMessageListenerContainer simpleMessageListenerContainer = createListenerContainer(queueName);
        simpleMessageListenerContainer.initialize();
        simpleMessageListenerContainer.start();
    }

    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    public RabbitTemplate amqpTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange(exchangeName);
        rabbitTemplate.setMessageConverter(new SimpleMessageConverter());
        rabbitTemplate.setUseDirectReplyToContainer(false);
        return rabbitTemplate;
    }

    Queue createQueue(String queueName) {
        return new Queue(queueName);
    }

    TopicExchange createTopicExchange(String exchangeName) {
        return new TopicExchange(exchangeName);
    }

    Binding createBinding(Queue queue, TopicExchange exchange, String routingKey) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // The main guy
    AmqpAdmin createAmqpAdmin(String queueName, String exchangeName) {
        Queue queue = createQueue(queueName);
        TopicExchange topicExchange = createTopicExchange(exchangeName);
        Binding binding = createBinding(queue, topicExchange, routingKey);

        AmqpAdmin amqpAdmin = new RabbitAdmin(connectionFactory());
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareExchange(topicExchange);
        amqpAdmin.declareBinding(binding);

        amqpAdmin.initialize();

        return amqpAdmin;
    }

    public SimpleMessageListenerContainer createListenerContainer(String queueName) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = connectionFactory();
        Connection connection = connectionFactory.createConnection();
        Channel channel = connection.createChannel(true);
        channel.close();
        connection.close();


        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(createQueue(queueName));
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setMissingQueuesFatal(false);
        container.setAmqpAdmin(createAmqpAdmin(queueName, exchangeName)); // Used when creating queues

        container.setMessageListener(new ChannelAwareMessageListener() {

            private String listenerId = UUID.randomUUID().toString();

            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                System.out.println("listenerId: " + listenerId);
                System.out.println("RabbitMQServiceImpl.java message.getBody(): " + new String(message.getBody()));
                System.out.println("queueName: " + queueName);

                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // This is for manual acknowledgement of this message.

                // TODO: Find out how many unacknowldeged messages yet
                // TODO: Perform send these messages to the WebSocket user. If the device received these messages successfully, confirm acknowledge these messages.
                channel.close();
            }
        });

        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }
}
