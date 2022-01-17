package com.adidas.subscriptionservice.service;

import com.adidas.subscriptionservice.dto.ResponseDTO;
import com.adidas.subscriptionservice.dto.SubscriptionDTO;
import org.springframework.stereotype.Service;

@Service
public interface ISubscriptionService {

    /**
     *
     */
    ResponseDTO createSubscription(SubscriptionDTO subscriptionDTO);

    /**
     *
     */
    ResponseDTO cancelSubscription(Integer id);

    /**
     *
     */
    ResponseDTO getSubscription(Integer id);

    /**
     *
     */
    ResponseDTO getSubscriptions();

}
