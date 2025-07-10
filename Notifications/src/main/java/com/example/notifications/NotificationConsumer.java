package com.example.notifications;

import com.example.notifications.entity.Notification;
import com.example.notifications.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationConsumer {

    @Autowired
    private NotificationPushService pushService;

    @Autowired
    private NotificationRepository repository;

    @KafkaListener(topics = "notifications", groupId = "notification-group")
    public void consume(Notification notification) {

        if (notification.getCreatedAt() == null) {
            notification.setCreatedAt(LocalDateTime.now());
        }
        notification.setRead(false);


        repository.save(notification);


        pushService.sendNotificationToUser(notification.getReceiver(), notification);
    }
}
