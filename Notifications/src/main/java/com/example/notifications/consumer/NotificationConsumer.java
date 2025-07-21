package com.example.notifications.consumer;

import com.example.notifications.entity.Notification;
import com.example.notifications.repository.NotificationRepository;
import com.example.notifications.service.NotificationPushService;
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





        pushService.sendNotificationToUser(notification.getReceiver(), notification);
    }
}
