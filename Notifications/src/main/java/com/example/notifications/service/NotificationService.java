package com.example.notifications.service;

import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import java.time.LocalDateTime;

import com.example.notifications.producer.NotificationProducer;
import com.example.notifications.entity.Notification;
import com.example.notifications.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Autowired
    private NotificationRepository repository;

    @Autowired
    private NotificationProducer producer;

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(String receiver, String message, String sender, String type, String link, String category, String kind,String Subject) {
        if ("team".equalsIgnoreCase(category)) {
            String url = "http://localhost:8090/api/team/employee/" + receiver;

            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            List<Map<String, Object>> teams = response.getBody();

            if (teams != null && !teams.isEmpty()) {
                Map<String, Object> team = teams.get(0);
                List<Map<String, Object>> employees = (List<Map<String, Object>>) team.get("employees");

                for (Map<String, Object> emp : employees) {
                    String employeeId = (String) emp.get("employeeId");

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
                            .subject(Subject)
                            .stared(false)
                            .build();

                    repository.save(notification);
                    producer.sendNotification(notification);
                }
            }
        } else if ("department".equalsIgnoreCase(category)) {
            String url = "http://localhost:8090/api/department/" + receiver + "/employees";

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            Map<String, Object> department = response.getBody();

            if (department != null && department.containsKey("employeeList")) {
                List<Map<String, Object>> employees = (List<Map<String, Object>>) department.get("employeeList");

                for (Map<String, Object> emp : employees) {
                    String employeeId = (String) emp.get("employeeId");

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
                            .stared(false)
                            .subject(Subject)
                            .build();

                    repository.save(notification);
                    producer.sendNotification(notification);
                }
            }
        } else {

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
                    .stared(false)
                    .subject(Subject)
                    .build();

            repository.save(notification);
            producer.sendNotification(notification);
        }
    }


    @Cacheable(value = "unreadNotifications", key = "#receiver")
    public List<Notification> getUnreadNotifications(String receiver) {
        return repository.findByReceiverAndReadFalseOrderByCreatedAtDesc(receiver);
    }

    public void startMessage(Long id){
        repository.findById(id).ifPresent(notification -> {
            notification.setStared(true);
            repository.save(notification);
            String receiver = notification.getReceiver();
            evictCache(receiver);
        });
    }
    public void unStartMessage(Long id){
        repository.findById(id).ifPresent(notification -> {
            notification.setStared(false);
            repository.save(notification);
            String receiver = notification.getReceiver();
            evictCache(receiver);
        });
    }

    @Transactional
    public boolean deleteMessage(Long id) {
        Optional<Notification> optional = repository.findById(id);
        if (optional.isPresent()) {
            Notification notification = optional.get();
            repository.deleteById(id);
            evictCache(notification.getReceiver());
            return true;
        }
        return false;
    }



    @Cacheable(value = "getAllNotifications", key = "#receiver")
    public List<Notification> getAllNotifications(String receiver) {
        return repository.findByReceiverOrderByCreatedAtDesc(receiver);
    }

    public void markAsRead(Long id) {
        repository.findById(id).ifPresent(notification -> {
            notification.setRead(true);
            repository.save(notification);
            String receiver = notification.getReceiver();
            evictCache(receiver);
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

}
