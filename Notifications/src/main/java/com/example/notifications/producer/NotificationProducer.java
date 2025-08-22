package com.example.notifications.producer;

import com.example.notifications.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NotificationProducer {



    @Autowired
    private RedisTemplate redisTemplate;

    public void sendNotification(Notification notification) {
        String topic = "notifications";
        String key = notification.getReceiver();

//        CompletableFuture<SendResult<String, Notification>> future = kafkaTemplate.send(topic, key, notification);
        redisTemplate.convertAndSend("notifications:all", notification);
//        future.whenComplete((result, ex) -> {
//            if (ex != null) {
//                System.err.printf(" Failed to send to %s: %s%n", key, ex.getMessage());
//            } else {
//                RecordMetadata metadata = result.getRecordMetadata();
//                System.out.printf("Notification sent to %s | Partition: %d | Offset: %d%n",
//                        key, metadata.partition(), metadata.offset());
//            }
//        });
    }
}
