package com.example.notifications.repository;

import com.example.notifications.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {


    List<Notification> findByReceiverAndReadFalse(String receiver);

    List<Notification> findByReceiverAndReadFalseOrderByCreatedAtDesc(String receiver);
    List<Notification> findByReceiverOrderByCreatedAtDesc(String receiver);
    long countByReceiverAndReadFalse(String receiver);
    List<Notification> findByDeletedTrue();


}
