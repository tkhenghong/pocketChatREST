package com.pocketchat.services.rabbitmq;

import com.rabbitmq.client.*;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class RabbitMQServiceImpl implements RabbitMQService {

    @Value("${rabbitmq.connection.factory.username}")
    String username;

    @Value("${rabbitmq.connection.factory.password}")
    String password;

    // Types of messages with format:
    // When sending,
    // Conversation Group Normal messages: Exchange ID=conversationGroupId, routing key=*.chatMessage
    // Conversation Group System messages: Exchange ID=conversationGroupId, routing key=*.systemMessage


    // How consumers(Users) will receive the messages:
    // Example user/userContact ID: g98sf0e8g0rsfead
    // Receive Conversation Group Normal messages: Exchange ID: conversationGroupId, queueName=g98sf0e8g0rsfead, routing key=g98sf0e8g0rsfead.chatMessage
    // Receive Conversation Group System messages: Exchange ID: conversationGroupId, queueName=g98sf0e8g0rsfead, routing key=g98sf0e8g0rsfead.systemMessage

    @Override
    public void declareExchange(String exchangeName) {

    }

    @Override
    public void declareQueue(String queueName) {

    }

    @Override
    public void declareBinding(String exchangeName, String queueName) {

    }

    @Override
    public void addMessageToQueue(String queueName, String exchangeName, String routingKey, String message) {
        createAmqpAdmin(queueName, exchangeName, routingKey); // Create exchanges with it's binding if needed.
        sendRabbitMQMessageOld(exchangeName, routingKey, message);
    }

    @Override
    public void listenMessagesFromQueue(String queueName, String exchangeName, String routingKey, String consumerTag) {
//        createAmqpAdmin(queueName, exchangeName, routingKey); // Create exchanges with it's binding if needed.
        // this.listenRabbitMQMessagesOld(queueName);
        listenRabbitMQMessagesNew(queueName, exchangeName, routingKey, consumerTag);
    }

    /**
     * Tried channel.basicPublish not yet succeed, use the more common one first.
     **/
    private void sendRabbitMQMessageOld(String exchangeName, String routingKey, String message) {
        AmqpTemplate amqpTemplate = amqpTemplate(exchangeName);
        amqpTemplate.convertAndSend(exchangeName, routingKey, message);
    }

    /**
     * Comprehensive way to listen to RabbitMQ message.
     *
     * @param queueName:    Name of the queue. Normally is user ID.
     * @param exchangeName: Name of the exchange. Normally is conversation group ID.
     * @param routingKey:   Routing key. Normally it means type of message wish to receive.
     * @param consumerTag:  Consumer tag. Normally it's the ID of the user. It will help to differentiate consumers that listen the messages from the exchange.
     *                      Steps:
     *                      1. Connect to RabbitMQ (Create connectionFactory)
     *                      2. Create Channel and Connection
     *                      3. Declare Queue, Exchanges and bind them together.
     *                      4. Get the remaining message count after Step 3 (must be in correct order, or else you won't get the correct remaining messages count)
     *                      5. If no messages, close the connection (not send anything to the user through WebSocket).
     *                      6. Read messages from the queue with a Consumer(Manual acknowledgement)
     *                      7. The message in handleDelivery() is coming in order. Delivery tag is the index number of the remaining messages.
     *                      8. Send the message to user through WebSocket.
     *                      9. When delivery tag is same with remaining message count(finish reading the queue's messages), close the connection.
     */
    private void listenRabbitMQMessagesNew(String queueName, String exchangeName, String routingKey, String consumerTag) {

        ConnectionFactory connectionFactory = connectionFactory();
        Connection connection = connectionFactory.createConnection();
        Channel channel = connection.createChannel(true);
        try {
            AMQP.Queue.DeclareOk queueDeclareOkResponse = channel.queueDeclare(queueName, true, false, false, null);
            AMQP.Exchange.DeclareOk exchangeDeclareOkResponse = channel.exchangeDeclare(exchangeName, "topic", true);
            AMQP.Queue.BindOk queueBindOkResponse = channel.queueBind(queueName, exchangeName, routingKey);

            Integer remainingMessageCount = queueDeclareOkResponse.getMessageCount();

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("handleDelivery()");
                    String routingKey = envelope.getRoutingKey();
                    System.out.println("routingKey: " + routingKey);
                    String contentType = properties.getContentType();
                    System.out.println("contenttype: " + contentType);
                    long deliveryTag = envelope.getDeliveryTag();
                    System.out.println("deliveryTag: " + deliveryTag);

                    String message = new String(body);

                    System.out.println("The message: " + message);
                    System.out.println("Remaining messages when in listener CHECK: " + queueDeclareOkResponse.getMessageCount());

                    // Send to websocket for each message
                    // Not recommend to gather all messages first


                    // If send to webSocket user successful, acknowledge this message.
                    channel.basicAck(deliveryTag, false);

                    // Else, not acknowledge this message, so this message will be redelivered again next time.

                    System.out.println("Acknowledged the message.");

                    if (remainingMessageCount == deliveryTag) {
                        System.out.println("if (remainingMessageCount == deliveryTag)");
                        closeChannelAndConnection(connection, channel, consumerTag);
                        // TODO: Gathered all messages? Good let's them to the WebSocket.
                        // this.sendToUserWebSocket();
                    }
                }

                @Override
                public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
                    super.handleShutdownSignal(consumerTag, sig);
                }
            };

            channel.basicConsume(queueName, false, consumerTag, consumer);

            System.out.println("Done received messages.");
            // If no messages at all
            if (remainingMessageCount == 0) {
                closeChannelAndConnection(connection, channel, consumerTag);
            }
        } catch (IOException exceptions) {
            exceptions.printStackTrace();
        }
    }

    private void closeChannelAndConnection(Connection connection, Channel channel, String consumerTag) {
        System.out.println("closeChannelAndConnection()");
        try {
            channel.basicCancel(consumerTag);
            channel.close(); // Might throw Timeout Exception

            connection.close();
        } catch (TimeoutException | IOException e) {
            System.out.println("Error during closing connection.");
            e.printStackTrace();
        }
    }

    private ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    public RabbitTemplate amqpTemplate(String exchangeName) {
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
    // TODO: Need a way to tell that is process is successful or failure (error management)
    RabbitAdmin createAmqpAdmin(String queueName, String exchangeName, String routingKey) {
        Queue queue = createQueue(queueName);
        TopicExchange topicExchange = createTopicExchange(exchangeName);
        Binding binding = createBinding(queue, topicExchange, routingKey);

        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());

        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(topicExchange);
        rabbitAdmin.declareBinding(binding);

        rabbitAdmin.initialize();

        return rabbitAdmin;
    }
}
