package com.example.notifications.service;

import com.example.notifications.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Value("${from.email.address}")
    private String fromEmailAddress;

    @Autowired
    private JavaMailSender mailSender;


    public void sendEmail(Notification notification) {
        String content = """
            <!DOCTYPE html>
            <html>
            <head>
              <meta charset="UTF-8">
              <title>Notification</title>
            </head>
            <body style="margin:0; padding:0; font-family: Arial, sans-serif; background:#f4f4f4;">
            
              <table width="100%" cellpadding="0" cellspacing="0" style="background:#f4f4f4; padding:20px;">
                <tr>
                  <td align="center">
            
                    <!-- Card -->
                    <table width="600" cellpadding="0" cellspacing="0" style="background:#ffffff; border-radius:8px; overflow:hidden; box-shadow:0 4px 8px rgba(0,0,0,0.05);">
                      <!-- Header -->
                      <tr>
                        <td style="background:#4CAF50; padding:20px; color:white; font-size:20px; font-weight:bold; text-align:center;">
                         Notification from ${sender}
                        </td>
                      </tr>
            
                      <!-- Body -->
                      <tr>
                        <td style="padding:30px; color:#333; font-size:15px; line-height:1.6;">
                          <p>Hi <strong>${receiver}</strong>,</p>
                          <p>${message}</p>
            
                          <table style="margin:20px 0; width:100%; border-collapse:collapse;">
                            <tr>
                              <td style="padding:8px; border:1px solid #ddd;"><strong>Type</strong></td>
                              <td style="padding:8px; border:1px solid #ddd;">${type}</td>
                            </tr>
                            <tr>
                              <td style="padding:8px; border:1px solid #ddd;"><strong>Category</strong></td>
                              <td style="padding:8px; border:1px solid #ddd;">${category}</td>
                            </tr>
                            <tr>
                              <td style="padding:8px; border:1px solid #ddd;"><strong>Kind</strong></td>
                              <td style="padding:8px; border:1px solid #ddd;">${kind}</td>
                            </tr>
                          </table>
            
                          <div style="text-align:center; margin-top:30px;">
                            <a href="${link}"
                               style="background:#4CAF50; color:#fff; text-decoration:none; padding:12px 24px; border-radius:6px; display:inline-block; font-weight:bold;">
                              🔗 View Details
                            </a>
                          </div>
            
                          <p style="margin-top:30px; font-size:13px; color:#888;">
                            If you did not expect this notification, you can safely ignore this email.
                          </p>
                        </td>
                      </tr>
            
                      <!-- Footer -->
                      <tr>
                        <td style="background:#f9f9f9; text-align:center; padding:15px; font-size:12px; color:#777;">
                          © 2025 Your Company. All rights reserved.
                        </td>
                      </tr>
                    </table>
            
                  </td>
                </tr>
              </table>
            
            </body>
            </html>
            """;

// Replace placeholders safely
        content = content
                .replace("${receiver}", notification.getReceiver())
                .replace("${message}", notification.getMessage())
                .replace("${type}", notification.getType())
                .replace("${category}", notification.getCategory())
                .replace("${kind}", notification.getKind())
                .replace("${link}", notification.getLink())
                .replace("${sender}", notification.getSender());


        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmailAddress);
            helper.setTo(notification.getReceiver());
            helper.setSubject(notification.getSubject());
            helper.setText(content, true);

            mailSender.send(message);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

}
