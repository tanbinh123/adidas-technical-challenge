package com.adidas.subscriptionservice.service;

import com.adidas.subscriptionservice.dto.ResponseDTO;
import com.adidas.subscriptionservice.dto.SubscriptionDTO;
import org.springframework.stereotype.Service;

@Service
public interface ISubscriptionService {

    /**
     * Save subscription data in database and sends Kafka message to Email Service.
     *
     * @param subscriptionDTO JSON received from Public Service or secured service
     * @return                Http Response
     */
    ResponseDTO createSubscription(SubscriptionDTO subscriptionDTO);

    /**
     * Change Consent property flag to false to cancel subscription
     *
     * @param id Subscription ID
     * @return   Http Response
     */
    ResponseDTO cancelSubscription(Integer id);

    /**
     * Get subscription data from database by ID
     *
     * @param id Subscription ID
     * @return   Http Response
     */
    ResponseDTO getSubscription(Integer id);

    /**
     * Get all subscription data from database
     *
     * @return   Http Response
     */
    ResponseDTO getSubscriptions();

}
