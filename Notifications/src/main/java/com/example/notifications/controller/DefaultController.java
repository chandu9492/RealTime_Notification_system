package com.example.notifications.controller;

import com.example.notifications.entity.Notification;
import com.example.notifications.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notification")
public class DefaultController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/email")
    public String index(Notification notification) {

        try {
            emailService.sendEmail(notification);
            return " Email sent successfully";
        } catch (RuntimeException e) {
            e.printStackTrace();
            return " Failed to send email: " + e.getMessage();
        }
    }
}
