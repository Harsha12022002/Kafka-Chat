package com.example.springkafka.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final String TOPIC = "notification-topic";

    public void sendNotification(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
