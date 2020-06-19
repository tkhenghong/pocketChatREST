package com.pocketchat.controllers.rabbitmq;

import com.pocketchat.models.controllers.request.rabbitmq.AddMessageToQueueRequest;
import com.pocketchat.models.controllers.request.rabbitmq.ListenMessagesFromQueueRequest;
import com.pocketchat.services.rabbitmq.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQTestController {
    RabbitMQService rabbitMQService;

    @Autowired
    RabbitMQTestController(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    @PostMapping("")
    public void addMessageToQueue(@RequestBody AddMessageToQueueRequest addMessageToQueueRequest) throws InterruptedException {
        System.out.println("RabbitMQTestController.java addMessageToQueue()");
        rabbitMQService.addMessageToQueue(addMessageToQueueRequest.getQueueName(), addMessageToQueueRequest.getExchangeName(), addMessageToQueueRequest.getRoutingKey(), addMessageToQueueRequest.getMessage());
    }

    @GetMapping("")
    public void listenMessagesFromQueue(@RequestBody ListenMessagesFromQueueRequest listenMessagesFromQueueRequest) {
        System.out.println("RabbitMQTestController.java listenMessagesFromQueue()");
        rabbitMQService.listenMessagesFromQueue(listenMessagesFromQueueRequest.getQueueName(), listenMessagesFromQueueRequest.getExchangeName(), listenMessagesFromQueueRequest.getRoutingKey(), listenMessagesFromQueueRequest.getConsumerTag());
    }
}
