package com.adidas.subscriptionservice.service;

import com.adidas.subscriptionservice.dto.ResponseDTO;
import com.adidas.subscriptionservice.dto.SubscriptionDTO;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public interface ISubscriptionService {

    /**
     *
     */
    ResponseDTO createSubscription(SubscriptionDTO subscriptionDTO);

    /**
     *
     */
    ResponseDTO cancelSubscription(BigInteger id);

    /**
     *
     */
    ResponseDTO getSubscription(BigInteger id);

    /**
     *
     */
    ResponseDTO getSubscriptions();

}
