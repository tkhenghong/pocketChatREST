package com.pocketchat.server.configurations.schedulers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
public class SchedulerConfig {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;


}
