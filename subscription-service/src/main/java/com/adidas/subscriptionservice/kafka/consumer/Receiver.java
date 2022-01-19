package com.adidas.subscriptionservice.kafka.consumer;

import com.adidas.subscriptionservice.dto.SubscriptionDTO;
import com.adidas.subscriptionservice.service.ISubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private ISubscriptionService subscriptionService;

    /**
     * Receives SubscriptionDTO from Kafka server
     *
     * @param subscriptionDTO
     */
    @KafkaListener(topics = "${spring.kafka.topic.subscribe}")
    public void receive(SubscriptionDTO subscriptionDTO) {
        LOGGER.info("Received subscriptionDTO='{}'", subscriptionDTO);
        subscriptionService.createSubscription(subscriptionDTO);
        latch.countDown();
    }

}
