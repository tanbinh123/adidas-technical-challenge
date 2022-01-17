package com.adidas.subscriptionservice.kafka.producer;

import com.adidas.subscriptionservice.dto.SubscriptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;


public class Sender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, SubscriptionDTO> simpleKafkaTemplate;

    public void send(String topic, SubscriptionDTO subscriptionDTO) {
        LOGGER.info("sending subscriptionDTO='{}' to topic='{}'", subscriptionDTO, topic);
        simpleKafkaTemplate.send(topic, subscriptionDTO);
    }

}
