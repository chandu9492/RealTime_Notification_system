package com.example.notifications.consumer;

import com.example.notifications.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationConsumer {

    @Autowired
    private RedisTemplate<String, Notification> redisTemplate;

    @KafkaListener(topics = "notifications", groupId = "notification-group")
    public void consume(Notification notification) {

        if (notification.getCreatedAt() == null) {
            notification.setCreatedAt(LocalDateTime.now());
        }
        System.out.println("Got notification from Kafka: " + notification);
        notification.setRead(false);

        redisTemplate.convertAndSend("notifications:all", notification);
    }
}