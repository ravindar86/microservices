package com.microservice.notificationservice;

import com.microservice.notificationservice.service.EmailSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    // the method name input binder (notificationEventSupplier) should match with notificationEventSupplier-in-0
    @Bean
    public Consumer<String> notificationEventSupplier() {
        return message -> {
            new EmailSender().sendEmail(message);
        };
    }
}
