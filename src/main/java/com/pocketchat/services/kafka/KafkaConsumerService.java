package com.pocketchat.services.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.message.Message;
import com.pocketchat.services.models.conversationGroup.ConversationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.text.ParseException;

@Service
@PropertySource("classpath:/application.properties")
public class KafkaConsumerService {

    // Remember, NOT SimpleMessagingTemplate!
    private SimpMessagingTemplate simpMessagingTemplate;
    private KafkaTemplate kafkaTemplate;

    // Used to convert POJO classes to JSON and forth
    // link: https://www.baeldung.com/jackson-object-mapper-tutorial
    private ObjectMapper objectMapper;

    private ConversationGroupService conversationGroupService;

    @Autowired
    public KafkaConsumerService(SimpMessagingTemplate simpMessagingTemplate, ConversationGroupService conversationGroupService, KafkaTemplate kafkaTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.kafkaTemplate = kafkaTemplate;
        this.conversationGroupService = conversationGroupService;
    }


    @Value("${kafka.topic}")
    String kafkaTopicName;

    // Kafka will listen to this topic. kafka.topic=pocketChat.
    @KafkaListener(topicPattern = "${kafka.topic}")
    public void consume(@Payload Message message) {
        System.out.println("KafkaConsumerService.java consume()");
        System.out.println("KafkaConsumerService.java message.getId(): " + message.getId());
        System.out.println("KafkaConsumerService.java message.getMessageContent(): " + message.getMessageContent());
        // Step 1: Find the group that the message is belonged to
        ConversationGroup conversationGroup = conversationGroupService.getSingleConversation(message.getConversationId());
        if (conversationGroup.getId().equals(message.getConversationId())) {
            System.out.println("KafkaConsumerService.java if (conversationGroup.getId().equals(message.getConversationId()))");
            // Convert Message object to JSON string
            objectMapper = new ObjectMapper();

            String jsonString = null;
            try {
                jsonString = objectMapper.writeValueAsString(message);
                System.out.println("KafkaConsumerService.java JSON String mapping success.");
            } catch (JsonProcessingException e) {
                System.out.println("Error Parsing Object to String");
                throw new IllegalStateException(e);
            }

            System.out.println("KafkaConsumerService.java jsonString: " + jsonString);
            if (!StringUtils.isEmptyOrWhitespace(jsonString)) {
                System.out.println("KafkaConsumerService.java if (!StringUtils.isEmptyOrWhitespace(jsonString))");
                kafkaTemplate.send(message.getConversationId(), jsonString);
                System.out.println("KafkaConsumerService.java message sent!");
            }
        } else {
            System.out.println("KafkaConsumerService.java if (!conversationGroup.getId().equals(message.getConversationId()))");
        }


        // This line below will send that message to the topic of the WebSocket
        // IMPORTANT: This is the topic where The server will be sending to, and the user will be listening to, WITHIN Websocket
        simpMessagingTemplate.convertAndSend("/topic/pocketChat", message);
    }
}
