package com.example.notifications.controller;

import com.example.notifications.entity.Notification;
import com.example.notifications.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification) {
        notificationService.sendNotification(
                notification.getReceiver(),
                notification.getMessage(),
                notification.getSender(),
                notification.getType(),
                notification.getLink()
        );

        return ResponseEntity.ok("Notification sent");
    }

    @GetMapping("/unread/{user}")
    public ResponseEntity<List<Notification>> getUnread(@PathVariable String user) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(user));
    }

    @GetMapping("/all/{user}")
    public ResponseEntity<List<Notification>> getAll(@PathVariable String user) {
        return ResponseEntity.ok(notificationService.getAllNotifications(user));
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<String> markRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok("Notification marked as read");
    }
    @GetMapping("/unread-count/{receiver}")
    public ResponseEntity<Long> getUnreadCount(@PathVariable String receiver) {
        return ResponseEntity.ok(notificationService.getUnreadCount(receiver));
    }

}
