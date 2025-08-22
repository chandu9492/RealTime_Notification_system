package com.example.notifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableCaching
@EnableAsync
@EnableFeignClients(basePackages = "com.example.notifications.clients")
@SpringBootApplication
@EnableDiscoveryClient
public class NotificationsApplication {

	public static void main(String[] args) {

		SpringApplication.run(NotificationsApplication.class, args);
	}

}
