package com.example.notifications.service;
import org.springframework.cache.annotation.Cacheable;
import java.time.LocalDateTime;

import com.example.notifications.NotificationProducer;
import com.example.notifications.entity.Notification;
import com.example.notifications.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository repository;

    @Autowired
    private NotificationProducer producer;

    public void sendNotification(String receiver, String message, String sender, String type, String link,String category,String kind) {
        Notification notification = Notification.builder()
                .receiver(receiver)
                .message(message)
                .sender(sender)
                .type(type)
                .link(link)
                .read(false)
                .createdAt(LocalDateTime.now())
                .category(category)
                .kind(kind)
                .build();

        producer.sendNotification(notification);
    }

    @Cacheable(value = "unreadNotifications", key = "#receiver")
    public List<Notification> getUnreadNotifications(String receiver) {
        return repository.findByReceiverAndReadFalseOrderByCreatedAtDesc(receiver);
    }

    @Cacheable(value = "getAllNotifications", key = "#receiver")
    public List<Notification> getAllNotifications(String receiver) {
        return repository.findByReceiverOrderByCreatedAtDesc(receiver);
    }

    public void markAsRead(Long id) {
        repository.findById(id).ifPresent(notification -> {
            notification.setRead(true);
            repository.save(notification);

            // Invalidate cache when notification is marked as read
            String receiver = notification.getReceiver();
            evictCache(receiver);
        });
    }

    @Cacheable(value = "unreadCount", key = "#receiver")
    public Long getUnreadCount(String receiver) {
        return repository.countByReceiverAndReadFalse(receiver);
    }

    @CacheEvict(value = {"unreadNotifications", "allNotifications", "unreadCount"}, key = "#receiver")
    public void evictCache(String receiver) {
        // No body needed - Spring will handle the eviction
    }
}
