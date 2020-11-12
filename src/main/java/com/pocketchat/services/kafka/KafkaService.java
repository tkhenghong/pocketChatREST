package com.pocketchat.services.kafka;

import java.util.List;

public interface KafkaService {
    void addMessage(String id, String message);

    List<String> getMessages(String id);
}
