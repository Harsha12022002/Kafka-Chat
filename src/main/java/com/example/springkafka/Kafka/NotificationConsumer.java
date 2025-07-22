package com.example.springkafka.Kafka;

import com.example.springkafka.SimpleTextHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private final SimpleTextHandler simpleTextHandler;


    //can be done using lombok no need to code this one
    public NotificationConsumer(SimpleTextHandler simpleTextHandler) {
        this.simpleTextHandler = simpleTextHandler;
    }

    @KafkaListener(topics = "notification-topic", groupId = "notification_group")
    public void consume(String message) {
        System.out.println("Consumed from Kafka: " + message);
        try {
            simpleTextHandler.broadcast(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
