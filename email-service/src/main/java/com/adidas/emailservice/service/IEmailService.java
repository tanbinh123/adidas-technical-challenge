package com.adidas.emailservice.service;

import com.adidas.emailservice.dto.SubscriptionDTO;
import org.springframework.stereotype.Service;

@Service
public interface IEmailService {

    /**
     *
     * @param subscriptionDTO
     */
    void sendEmail(SubscriptionDTO subscriptionDTO);

}
