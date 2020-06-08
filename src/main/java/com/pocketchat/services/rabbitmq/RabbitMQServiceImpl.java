package com.pocketchat.services.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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

    String testingExchangeName = "test-exchange-in-spring-boot-rabbitmq-test";

    String testingRoutingKey = "testing.routing.key";

    // Types of messages with format:
    // When sending,
    // Conversation Group Normal messages: Exchange ID=conversationGroupId, routing key=*.chatMessage
    // Conversation Group System messages: Exchange ID=conversationGroupId, routing key=*.systemMessage


    // How consumers(Users) will receive the messages:
    // Example user/userContact ID: g98sf0e8g0rsfead
    // Receive Conversation Group Normal messages: Exchange ID: conversationGroupId, queueName=g98sf0e8g0rsfead, routing key=g98sf0e8g0rsfead.chatMessage
    // Receive Conversation Group System messages: Exchange ID: conversationGroupId, queueName=g98sf0e8g0rsfead, routing key=g98sf0e8g0rsfead.systemMessage

    @Override
    public void addMessageToQueue(String queueName, String message) throws InterruptedException {
        this.sendRabbitMQMessageOld(queueName, testingExchangeName, message);
    }

    @Override
    public void listenMessagesFromQueue(String queueName) throws IOException, TimeoutException {
        this.listenRabbitMQMessagesNew(queueName, testingExchangeName, testingRoutingKey);
    }

    /**
     * Tried channel.basicPublish not yet succeed, use the more common one first.
     **/
    private void sendRabbitMQMessageOld(String queueName, String exchangeName, String message) {
        AmqpTemplate amqpTemplate = amqpTemplate();
        amqpTemplate.convertAndSend(exchangeName, queueName, message);
    }

    /**
     * Steps:
     * 1. Connect to RabbitMQ (Create connectionFactory)
     * 2. Create Channel and Connection
     * 3. Declare Queue, Exchanges and bind them together.
     * 4. Get the remaining message count after Step 3 (must be in correct order, or else you won't get the correct remaining messages count)
     * 5. If no messages, close the connection (not send anything to the user through WebSocket).
     * 6. Read messages from the queue with a Consumer(Manual acknowledgement)
     * 7. The message in handleDelivery() is coming in order. Delivery tag is the index number of the remaining messages.
     * 8. Send the message to user through WebSocket.
     * 9. When delivery tag is same with remaining message count(finish reading the queue's messages), close the connection.
     */
    private void listenRabbitMQMessagesNew(String queueName, String exchangeName, String routingKey) {
        System.out.println("receiveMessagesFromRabbitMQ()");
        System.out.println("queueName: " + queueName);
        System.out.println("exchangeName: " + exchangeName);
        System.out.println("routingKey: " + routingKey);

        ConnectionFactory connectionFactory = connectionFactory();
        System.out.println("connection factory created.");
        Connection connection = connectionFactory.createConnection();
        System.out.println("Connection created.");
        Channel channel = connection.createChannel(true);
        System.out.println("Channel created.");
        try {
            AMQP.Queue.DeclareOk queueDeclareOkResponse = channel.queueDeclare(queueName, true, false, false, null);
            System.out.println("Declared queue.");
            AMQP.Exchange.DeclareOk exchangeDeclareOkResponse = channel.exchangeDeclare(exchangeName, "topic", true);
            System.out.println("Declared Exchange.");
            AMQP.Queue.BindOk queueBindOkResponse = channel.queueBind(queueName, exchangeName, routingKey);
            System.out.println("Binded queue with exchange.");

            System.out.println("queueDeclareOkResponse.getMessageCount(): " + queueDeclareOkResponse.getMessageCount());
            System.out.println("queueDeclareOkResponse.getConsumerCount(): " + queueDeclareOkResponse.getConsumerCount());

            Integer remainingMessageCount = queueDeclareOkResponse.getMessageCount();

            channel.basicConsume(queueName, false, "myConsumerTag", new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("handleDelivery()");
                    String routingKey = envelope.getRoutingKey();
                    System.out.println("routingKey: " + routingKey);
                    String contentType = properties.getContentType();
                    System.out.println("contentType: " + contentType);
                    long deliveryTag = envelope.getDeliveryTag();
                    System.out.println("deliveryTag: " + deliveryTag);

                    String message = new String(body);

                    System.out.println("The message: " + message);

                    // Send to websocket for each message
                    // Not recommend to gather all messages first


                    // If send to webSocket user successful, acknowledge this message.
                    channel.basicAck(deliveryTag, false);

                    // Else, not acknowledge this message, so this message will be redelivered again next time.

                    System.out.println("Acknowledged the message.");

                    if (remainingMessageCount == deliveryTag) {
                        System.out.println("if (remainingMessageCount == deliveryTag)");
                        closeChannelAndConnection(connection, channel);
                        // TODO: Gathered all messages? Good let's them to the WebSocket.
                        // this.sendToUserWebSocket();
                    }
                }
            });

            System.out.println("Done received messages.");
            // If no messages at all
            if (remainingMessageCount == 0) {
                closeChannelAndConnection(connection, channel);
            }
        } catch (IOException exceptions) {
            System.out.println("Error when declaring Queue/Exchange/Binding/Sending message.");
            System.out.println("Exception message: " + exceptions.getMessage());
            System.out.println("Exception toString(): " + exceptions.toString());
            exceptions.printStackTrace();
        }
    }

    private void closeChannelAndConnection(Connection connection, Channel channel) {
        System.out.println("closeChannelAndConnection()");
        try {
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

    private RabbitTemplate amqpTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange(testingExchangeName);
        rabbitTemplate.setMessageConverter(new SimpleMessageConverter());
        rabbitTemplate.setUseDirectReplyToContainer(false);
        return rabbitTemplate;
    }
}
