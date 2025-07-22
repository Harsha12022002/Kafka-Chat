package com.example.springkafka.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.springkafka.Kafka.NotificationProducer;

@RestController
@RequestMapping("/api/notify")
public class NotificationController {

    @Autowired
    private NotificationProducer producer;

    @PostMapping
    public String send(@RequestParam String message) {
        producer.sendNotification(message);
        return "Notification Queued!";
    }

  
}


