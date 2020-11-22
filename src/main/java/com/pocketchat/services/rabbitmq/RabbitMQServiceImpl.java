package com.pocketchat.services.rabbitmq;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Steps:
 * 1. Connect to RabbitMQ (Create connectionFactory).
 * 2. Create Channel and Connection.
 * 3. Declare Queue, Exchanges and bind them together.
 * 4. Get the remaining message count after Step 3 (must be in correct order, or else you won't get the correct remaining messages count)
 * 5. If no messages, close the connection (not send anything to the user through WebSocket).
 * 6. Read messages from the queue with a Consumer(Manual acknowledgement)
 * 7. The message in handleDelivery() is coming in order. Delivery tag is the index number of the remaining messages.
 * 8. Send the message to user through WebSocket.
 * 9. When delivery tag is same with remaining message count(finish reading the queue's messages), close the connection.
 * <p>
 * NOTES:
 * 1. Use empty exchange name as an argument will make the messages' that you're sending through using default RabbitMQ exchange.
 */
@Service
public class RabbitMQServiceImpl implements RabbitMQService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String username;

    private final String password;

    @Autowired
    RabbitMQServiceImpl(@Value("${rabbitmq.connection.factory.username}") String username,
                        @Value("${rabbitmq.connection.factory.password}") String password) {
        this.username = username;
        this.password = password;
    }

    // Types of messages with format:
    // When sending,
    // Conversation Group Normal messages: Exchange ID=conversationGroupId, routing key=*.chatMessage
    // Conversation Group System messages: Exchange ID=conversationGroupId, routing key=*.systemMessage


    // How consumers(Users) will receive the messages:
    // Example user/userContact ID: g98sf0e8g0rsfead
    // Receive Conversation Group Normal messages: Exchange ID: conversationGroupId, queueName=g98sf0e8g0rsfead, routing key=g98sf0e8g0rsfead.chatMessage
    // Receive Conversation Group System messages: Exchange ID: conversationGroupId, queueName=g98sf0e8g0rsfead, routing key=g98sf0e8g0rsfead.systemMessage

    /**
     * Create connections to the RabbitMQ.
     *
     * @return Connection object.
     */
    @Override
    public Connection createConnection() {
        ConnectionFactory connectionFactory = connectionFactory();
        return connectionFactory.createConnection();
    }

    /**
     * Send message into RabbitMQ queue.
     *
     * @param queueName:    Name of the queue. Typically an UserContact ID.
     * @param exchangeName: Name of the exchange. Typically an ConversationGroup ID.
     * @param routingKey:   A string used to filter which type of message should be retrieved.
     * @param message:      A JSON message converted from object. Typically a WebSocketMessage object.
     */
    @Override
    public void addMessageToQueue(String queueName, String exchangeName, String routingKey, String message) {
        // RabbitAdmin rabbitAdmin = createAmqpAdmin(queueName, exchangeName, routingKey); // Create exchanges with it's binding if needed.
        sendRabbitMQMessage(exchangeName, routingKey, message);
    }

    /**
     * Get messages from RabbitMQ queue.
     *
     * @param queueName:   Name of the queue. Typically an UserContact ID.
     * @param consumerTag: Consumer ID to be identified in RabbitMQ. This is needee to identify the user when consuming RabbitMQ.
     */
    @Override
    public void listenMessagesFromQueue(String queueName, String consumerTag) {
        listenRabbitMQMessagesNew(queueName, consumerTag);
    }

    /**
     * Send a message to RabbitMQ, using standard AmqpTemplate object.
     **/
    private void sendRabbitMQMessage(String exchangeName, String routingKey, String message) {
        AmqpTemplate amqpTemplate = amqpTemplate(exchangeName);
        amqpTemplate.convertAndSend(exchangeName, routingKey, message);
    }

    /**
     * Get access to the queue with custom Connection object.
     *
     * @param connection: Connection object which has the connection of the RabbitMQ.
     * @return Channel object which can be used for creating Consumer objects for consuming messages.
     */
    @Override
    public Channel getChannel(Connection connection) {
        return connection.createChannel(true);
    }

    /**
     * Get access to the RabbitMQ by getting Channel object.
     * <p>
     * NOTE: Connection configuration depends from the application.properties files.
     *
     * @return Channel object which can be used for creating Consumer objects for consuming messages.
     */
    @Override
    public Channel getChannel() {
        Connection connection = createConnection();
        return connection.createChannel(true);
    }

    /**
     * Comprehensive way to listen to RabbitMQ message.
     * <p>
     * NOTE: You don't need exchange name and routing key to consume a RabbitMQ queue.
     * NOTE: Not using this for consuming messages. This is only used for testing in RabbitMQController.
     *
     * @param queueName:   Name of the queue. Normally is UserContact ID.
     * @param consumerTag: Consumer tag. Normally it's the ID of the user. It will help to differentiate consumers that listen the messages from the exchange.
     */
    private void listenRabbitMQMessagesNew(String queueName, String consumerTag) {
        logger.info("Getting {} messages in RabbitMQ.", queueName);

        Connection connection = createConnection();
        Channel channel = connection.createChannel(true);
        try {
            AMQP.Queue.DeclareOk queueDeclareOkResponse = channel.queueDeclare(queueName, true, false, false, null);

            int remainingMessageCount = queueDeclareOkResponse.getMessageCount();

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    logger.info("handleDelivery()");
                    String routingKey = envelope.getRoutingKey();
                    logger.info("routingKey: {}", routingKey);
                    String contentType = properties.getContentType();
                    logger.info("contenttype: {}", contentType);
                    long deliveryTag = envelope.getDeliveryTag();
                    logger.info("deliveryTag: {}", deliveryTag);

                    String message = new String(body);

                    logger.info("The message: {}", message);
                    logger.info("Remaining message count from queue {}: {}", queueName, queueDeclareOkResponse.getMessageCount());

                    // Send to websocket for each message
                    // Not recommend to gather all messages first

                    // If send to webSocket user successful, acknowledge this message.
                    channel.basicAck(deliveryTag, false);

                    // Else, not acknowledge this message, so this message will be redelivered again next time.

                    logger.info("Acknowledged the message.");

                    if (remainingMessageCount == deliveryTag) {
                        logger.info("Finish reading messages for {}.", queueName);
                        closeChannelAndConnection(connection, channel, consumerTag);
                    }
                }

                @Override
                public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
                    super.handleShutdownSignal(consumerTag, sig);
                }
            };

            channel.basicConsume(queueName, false, consumerTag, consumer);

            logger.info("Done received messages.");
            // If no messages at all
            if (remainingMessageCount == 0) {
                closeChannelAndConnection(connection, channel, consumerTag);
            }
        } catch (IOException exceptions) {
            exceptions.printStackTrace();
        }
    }

    /**
     * Close RabbitMQ connection.
     *
     * @param connection:  Connection object created from within @method listenRabbitMQMessagesNew().
     * @param channel:     Channel object created from within @method listenRabbitMQMessagesNew().
     * @param consumerTag: An ID for the RabbitMQ consumer. RabbitMQ requires a name from the consumer when using RabbitMQ.
     */
    @Override
    public Boolean closeChannelAndConnection(Connection connection, Channel channel, String consumerTag) {
        try {
            channel.basicCancel(consumerTag);
            channel.close(); // Might throw Timeout Exception
            connection.close();
            logger.info("{}'s RabbitMQ connection and channel has been closed.", consumerTag);
            return true;
        } catch (TimeoutException | IOException e) {
            logger.error("Error during closing connection.", e);
        }

        return false;
    }

    /**
     * Create a factory for connecting RabbitMQ.
     *
     * @return ConnectionFactory object, ready for connect RabbitMQ.
     */
    private ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    /**
     * Create a template for AMQP, typically RabbitMQ.
     *
     * @param exchangeName: Name of exchange. Typically a ConversationGroup ID.
     * @return RabbitTemplate object ready to be used to add/retrieve messages.
     */
    public RabbitTemplate amqpTemplate(String exchangeName) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange(exchangeName);
        rabbitTemplate.setMessageConverter(new SimpleMessageConverter());
        rabbitTemplate.setUseDirectReplyToContainer(false);
        return rabbitTemplate;
    }

    /**
     * Create/Get a queue into RabbitMQ.
     *
     * @param queueName: Name of a queue. Typically an ID of UserContact.
     * @return Queue object to be used in connecting RabbitMQ queue.
     */
    Queue createQueue(String queueName) {
        return new Queue(queueName);
    }

    TopicExchange createTopicExchange(String exchangeName) {
        return new TopicExchange(exchangeName);
    }

    Binding createBinding(Queue queue, TopicExchange exchange, String routingKey) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    /**
     * (Main) Create an Admin to manage the AMQP, typically is RabbitMQ.
     * Create an admin to control RabbitMQ queues.
     * <p>
     * TODO: Need a way to tell that is process is successful or failure (error management)
     *
     * @param queueName:    Name of the queue. Typically an ID of UserContact object.
     * @param exchangeName: Name of the exchange. Typically an ID of ConversationGroup object.
     * @param routingKey:   A string that usually used to filter which type of message to be retrieved.
     * @return RabbitAdmin object to be used for send/retreive messages from RabbitMQ.
     */
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
