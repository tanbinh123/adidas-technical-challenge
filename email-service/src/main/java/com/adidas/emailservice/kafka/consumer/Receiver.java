package com.adidas.emailservice.kafka.consumer;

import com.adidas.emailservice.dto.SubscriptionDTO;
import com.adidas.emailservice.service.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private IEmailService emailService;

    @KafkaListener(topics = "${spring.kafka.topic.sendEmail}")
    public void receive(SubscriptionDTO subscriptionDTO) {
        LOGGER.info("received subscriptionDTO='{}'", subscriptionDTO);
        emailService.sendEmail(subscriptionDTO);
        latch.countDown();
    }

}
