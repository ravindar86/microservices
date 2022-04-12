package com.microservice.notificationservice.service;

import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    public void sendEmail(String orderNumber){
        System.out.println("Order placed successfully - Order Number is "+ orderNumber);
        System.out.println("Email Sent...");
    }
}
