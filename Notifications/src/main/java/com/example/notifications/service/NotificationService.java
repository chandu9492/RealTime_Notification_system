package com.example.notifications.service;

import com.example.notifications.dtos.EmployeeDepartmentDTO;
import com.example.notifications.dtos.EmployeeTeamResponse;
import com.example.notifications.dtos.TeamResponse;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import com.example.notifications.producer.NotificationProducer;
import com.example.notifications.entity.Notification;
import com.example.notifications.repository.NotificationRepository;

import com.example.notifications.clients.TeamClient;
import com.example.notifications.clients.DepartmentClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private NotificationRepository repository;

    @Autowired
    private NotificationProducer producer;

    @Autowired
    private TeamClient teamClient;

    @Autowired
    private DepartmentClient departmentClient;

    public void sendNotification(String receiver, String message, String sender, String type, String link, String category, String kind, String subject) {
        if ("team".equalsIgnoreCase(category)) {
            TeamResponse team = teamClient.getEmployeesInTeam(receiver);

            if (team != null && team.getEmployees() != null) {
                team.getEmployees().forEach(emp -> {
                    String employeeId = emp.getEmployeeId();

                    Notification notification = Notification.builder()
                            .receiver(employeeId)
                            .message(message)
                            .sender(sender)
                            .type(type)
                            .link(link)
                            .read(false)
                            .createdAt(LocalDateTime.now())
                            .category(category)
                            .kind(kind)
                            .subject(subject)
                            .stared(false)
                            .deleted(false)
                            .build();

                    sendNotificationAsync(notification);
                });
            }

        }if ("department".equalsIgnoreCase(category)) {
            EmployeeDepartmentDTO department = departmentClient.getEmployeesInDepartment(receiver);
            System.out.println(department);
            if (department != null && department.getEmployeeList() != null) {

                for (EmployeeTeamResponse emp : department.getEmployeeList()) {
                    System.out.println("not enter into for loop");
                    String employeeId = emp.getEmployeeId();

                    Notification notification = Notification.builder()
                            .receiver(employeeId)
                            .message(message)
                            .sender(sender)
                            .type(type)
                            .link(link)
                            .read(false)
                            .createdAt(LocalDateTime.now())
                            .category(category)
                            .kind(kind)
                            .subject(subject)
                            .stared(false)
                            .deleted(false)
                            .build();

                    sendNotificationAsync(notification);
                }
            }
        }

        else {
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
                    .subject(subject)
                    .stared(false)
                    .deleted(false)
                    .build();

            sendNotificationAsync(notification);
        }
    }

    @Async("notificationExecutor")
    public void sendNotificationAsync(Notification notification) {
        repository.save(notification);
        producer.sendNotification(notification);
    }

    @Cacheable(value = "unreadNotifications", key = "#receiver")
    public List<Notification> getUnreadNotifications(String receiver) {
        return repository.findByReceiverAndReadFalseOrderByCreatedAtDesc(receiver);
    }

    public void startMessage(Long id) {
        repository.findById(id).ifPresent(notification -> {
            notification.setStared(true);
            repository.save(notification);
            evictCache(notification.getReceiver());
        });
    }

    public void unStartMessage(Long id) {
        repository.findById(id).ifPresent(notification -> {
            notification.setStared(false);
            repository.save(notification);
            evictCache(notification.getReceiver());
        });
    }

    @Transactional
    public boolean deleteMessage(Long id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setDeleted(true);
        repository.save(notification);

        return true;
    }

    @Cacheable(value = "getAllNotifications", key = "#receiver")
    public List<Notification> getAllNotifications(String receiver) {
        return repository.findByReceiverOrderByCreatedAtDesc(receiver);
    }

    public void markAsRead(Long id) {
        repository.findById(id).ifPresent(notification -> {
            notification.setRead(true);
            repository.save(notification);
            evictCache(notification.getReceiver());
        });
    }

    @CachePut(value = "unreadCount", key = "#receiver")
    public Long getUnreadCount(String receiver) {
        return repository.countByReceiverAndReadFalse(receiver);
    }

    @CacheEvict(value = {"unreadNotifications", "getAllNotifications", "unreadCount"}, key = "#receiver")
    public void evictCache(String receiver) {
        redisTemplate.delete("unreadNotifications::" + receiver);
        redisTemplate.delete("getAllNotifications::" + receiver);
        redisTemplate.delete("unreadCount::" + receiver);
        System.out.println("Deleted Redis cache for receiver: " + receiver);
    }

    public List<Notification> deletedMessage() {
        return repository.findByDeletedTrue();
    }

}
