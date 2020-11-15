package com.pocketchat.controllers.rabbitmq;

import com.pocketchat.models.controllers.request.rabbitmq.AddMessageToQueueRequest;
import com.pocketchat.models.controllers.request.rabbitmq.ListenMessagesFromQueueRequest;
import com.pocketchat.services.rabbitmq.RabbitMQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQTestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    RabbitMQService rabbitMQService;

    @Autowired
    RabbitMQTestController(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    @PostMapping("")
    public void addMessageToQueue(@RequestBody AddMessageToQueueRequest addMessageToQueueRequest) throws InterruptedException {
        logger.info("addMessageToQueue()");
        rabbitMQService.addMessageToQueue(addMessageToQueueRequest.getQueueName(), addMessageToQueueRequest.getExchangeName(), addMessageToQueueRequest.getRoutingKey(), addMessageToQueueRequest.getMessage());
    }

    @GetMapping("")
    public void listenMessagesFromQueue(@RequestBody ListenMessagesFromQueueRequest listenMessagesFromQueueRequest) {
        logger.info("listenMessagesFromQueue()");
        rabbitMQService.listenMessagesFromQueue(listenMessagesFromQueueRequest.getQueueName(), listenMessagesFromQueueRequest.getConsumerTag());
    }
}
