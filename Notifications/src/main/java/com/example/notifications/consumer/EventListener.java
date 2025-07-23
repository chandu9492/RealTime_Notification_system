package com.example.notifications.consumer;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.example.notifications.entity.Notification;
import com.example.notifications.service.NotificationPushService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;
@Component
public class EventListener implements MessageListener {

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private NotificationPushService notificationController;

    private static final Logger log = LoggerFactory.getLogger(EventListener.class);


    
    public EventListener(ObjectMapper objectMapper, NotificationPushService notificationController) {
        this.objectMapper = objectMapper;
        this.notificationController = notificationController;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            log.info("New message received: {}", message);
            System.out.println("received notification from redis pubsub " + message.toString());
            Notification event = objectMapper.readValue(message.getBody(), Notification.class);
            notificationController.sendNotificationToUser(event.getReceiver(), event);
        } catch (IOException e) {
            log.error("Error while parsing message");
        }
    }
}
