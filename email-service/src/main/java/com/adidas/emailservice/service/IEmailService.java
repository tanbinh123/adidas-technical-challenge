package com.adidas.emailservice.service;

import com.adidas.emailservice.dto.SubscriptionDTO;
import org.springframework.stereotype.Service;

@Service
public interface IEmailService {

    /**
     * Method that sends email to the subscriber
     *
     * @param subscriptionDTO   JSON received from Subscription Service
     */
    void sendEmail(SubscriptionDTO subscriptionDTO);

}
