package com.example.notification_service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Slf4j
@Service
public class EmailSender {

    public void sendEmail(String orderNumber){
        log.info("Order Created.. Order no {}",orderNumber);
    }
}
