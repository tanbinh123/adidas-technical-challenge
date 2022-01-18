package com.adidas.publicservice.service;

import com.adidas.publicservice.dto.ResponseDTO;
import com.adidas.publicservice.dto.SubscriptionDTO;

public interface IPublicService {

    /**
     * Sends subscription data to Kafka server to subscribe the user in Subscription Service.
     *
     * @param subscriptionDTO Params received from the UI frontend
     * @return                Returns a DTO that contains the Http response
     */
    ResponseDTO createSubscription(SubscriptionDTO subscriptionDTO);

}
