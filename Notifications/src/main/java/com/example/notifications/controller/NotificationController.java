package com.example.notifications.controller;

import com.example.notifications.producer.NotificationProducer;
import com.example.notifications.service.NotificationPushService;
import com.example.notifications.entity.Notification;
import com.example.notifications.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")

public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationProducer producer;

    @Autowired
    private NotificationPushService pushService;
    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification) {
        notificationService.sendNotification(
                notification.getReceiver(),
                notification.getMessage(),
                notification.getSender(),
                notification.getType(),
                notification.getLink(),
                notification.getCategory(),
                notification.getKind(),
                notification.getSubject()
        );
        return ResponseEntity.ok("Notification sent");
    }
    @PostMapping("/sendList")
    public ResponseEntity<String> sendNotificationList(@RequestBody List<Notification> notifications) {
        for(Notification notification:notifications) {
            notificationService.sendNotification(
                    notification.getReceiver(),
                    notification.getMessage(),
                    notification.getSender(),
                    notification.getType(),
                    notification.getLink(),
                    notification.getCategory(),
                    notification.getKind(),
                    notification.getSubject()
            );
        }
        return ResponseEntity.ok("Notification sent");
    }

    @GetMapping("/unread/{user}")
    public ResponseEntity<List<Notification>> getUnread(@PathVariable String user) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(user));
    }

    @PutMapping("/stared/{messageId}")
    public String Stared(@PathVariable Long messageId){
        notificationService.startMessage(messageId);
        return "done";
    }

    @DeleteMapping("/delete/{messageId}")
    public String delete(@PathVariable Long messageId){
        notificationService.deleteMessage(messageId);
        return "done";
    }

    @GetMapping("/all/{user}")
    public ResponseEntity<List<Notification>> getAll(@PathVariable String user) {
        return ResponseEntity.ok(notificationService.getAllNotifications(user));
    }

    @PostMapping("/read/{id}")
    public ResponseEntity<String> markRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok("Notification marked as read");
    }

    @GetMapping("/unread-count/{receiver}")
    public ResponseEntity<Long> getUnreadCount(@PathVariable String receiver) {
        return ResponseEntity.ok(notificationService.getUnreadCount(receiver));
    }

    @GetMapping("/isOnline/{username}")
    public boolean isOnline(@PathVariable String username){
        return pushService.isOnline(username);
    }

    @GetMapping("/subscribe/{username}")
    public SseEmitter subscribe(@PathVariable String username) {
        return pushService.subscribe(username);
    }

    @GetMapping("/receive/{userId}")
    public SseEmitter receive(@PathVariable String userId) {
        return pushService.subscribe(userId); // Only unread + live messages
    }

    @GetMapping("/unSubscribe/{username}")
    public String unSubscribe(@PathVariable String username){
        return pushService.unSubscribe(username);
    }
    @GetMapping("/deletedMessages")
    public List<Notification> getDeletedNotifications() {
        return notificationService.deletedMessage();
    }

}
