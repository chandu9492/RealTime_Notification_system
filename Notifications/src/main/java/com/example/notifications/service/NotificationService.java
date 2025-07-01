package com.example.notifications.service;

import com.example.notifications.entity.Notification;
import com.example.notifications.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void sendNotification(String receiver, String message, String sender, String type, String link) {
        Notification notification = Notification.builder()
                .receiver(receiver)
                .message(message)
                .sender(sender)
                .type(type)
                .link(link)
                .build();
        notificationRepository.save(notification);
    }

    public List<Notification> getUnreadNotifications(String receiver) {
        return notificationRepository.findByReceiverAndReadFalse(receiver);
    }

    public List<Notification> getAllNotifications(String receiver) {
        return notificationRepository.findByReceiverOrderByCreatedAtDesc(receiver);
    }

    public void markAsRead(Long id) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found"));
        n.setRead(true);
        notificationRepository.save(n);
    }
    public long getUnreadCount(String receiver) {
        return notificationRepository.countByReceiverAndReadFalse(receiver);
    }

}
