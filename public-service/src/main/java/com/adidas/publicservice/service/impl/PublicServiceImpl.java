package com.adidas.publicservice.service.impl;

import com.adidas.publicservice.dto.ResponseDTO;
import com.adidas.publicservice.dto.SubscriptionDTO;
import com.adidas.publicservice.kafka.producer.Sender;
import com.adidas.publicservice.service.IPublicService;
import com.adidas.publicservice.util.ConstantsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PublicServiceImpl implements IPublicService {

    @Value("${spring.kafka.topic.subscribe}")
    private String SUBSCRIBE_TOPIC;

    private Sender sender;

    @Autowired
    PublicServiceImpl(Sender sender) {
        this.sender = sender;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(PublicServiceImpl.class);

    /**
     * Sends subscription data to Kafka server to subscribe the user in Subscription Service.
     *
     * @param subscriptionDTO Params received from the UI frontend
     * @return                Returns a DTO that contains the Http response
     */
    public ResponseDTO createSubscription(SubscriptionDTO subscriptionDTO) {

        try {
            sender.send(SUBSCRIBE_TOPIC, subscriptionDTO);
            return new ResponseDTO(HttpStatus.OK, ConstantsUtil.SUBSCRIPTION_OK);
        } catch (Exception e) {
            LOGGER.info("Error in PublicServiceImpl sending subscriptionDTO to Kafka server: '{}'", e.getMessage());
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, ConstantsUtil.SUBSCRIPTION_KO);
        }

    }

}
