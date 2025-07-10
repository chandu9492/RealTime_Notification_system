package com.example.notifications;

import com.example.notifications.entity.Notification;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NotificationProducer {

    @Autowired
    private KafkaTemplate<String, Notification> kafkaTemplate;

    public void sendNotification(Notification notification) {
        String topic = "notifications";
        String key = notification.getReceiver();

        CompletableFuture<SendResult<String, Notification>> future = kafkaTemplate.send(topic, key, notification);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.printf(" Failed to send to %s: %s%n", key, ex.getMessage());
            } else {
                RecordMetadata metadata = result.getRecordMetadata();
                System.out.printf("Notification sent to %s | Partition: %d | Offset: %d%n",
                        key, metadata.partition(), metadata.offset());
            }
        });
    }
}
