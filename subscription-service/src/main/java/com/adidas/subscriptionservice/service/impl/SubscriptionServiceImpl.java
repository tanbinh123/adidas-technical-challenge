package com.adidas.subscriptionservice.service.impl;

import com.adidas.subscriptionservice.dto.ResponseDTO;
import com.adidas.subscriptionservice.dto.SubscriptionDTO;
import com.adidas.subscriptionservice.entity.Subscription;
import com.adidas.subscriptionservice.repository.ISubscriptionRepository;
import com.adidas.subscriptionservice.service.ISubscriptionService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@AllArgsConstructor
public class SubscriptionServiceImpl implements ISubscriptionService {

    @Autowired
    private ISubscriptionRepository subscriptionRepository;

    public ResponseDTO createSubscription(SubscriptionDTO subscriptionDTO) {

        return null;
    }

    public ResponseDTO cancelSubscription(BigInteger id) {

        return null;
    }

    public ResponseDTO getSubscription(BigInteger id) {

        ResponseDTO responseDTO = new ResponseDTO();
        Subscription subscription = getSubscriptionById(id);

        if (subscription == null) {
            responseDTO.setResponseCode(HttpStatus.NO_CONTENT);
            responseDTO.setResponse("Invalid subscription ID");
        } else {
            responseDTO.setResponseCode(HttpStatus.OK);
            responseDTO.setResponse(subscription);
        }

        return responseDTO;
    }

    public ResponseDTO getSubscriptions() {

        ResponseDTO responseDTO = new ResponseDTO();
        List<Subscription> subscriptionList = getAllSubscriptions();

        if (subscriptionList == null || subscriptionList.isEmpty()) {
            responseDTO.setResponseCode(HttpStatus.NO_CONTENT);
            responseDTO.setResponse("No subscriptions in data base");
        } else {
            responseDTO.setResponseCode(HttpStatus.OK);
            responseDTO.setResponse(subscriptionList);
        }

        return responseDTO;
    }

    private Subscription getSubscriptionById(BigInteger id) {

        return subscriptionRepository.findById(id);
    }

    private List<Subscription> getAllSubscriptions() {

        return subscriptionRepository.findAll();
    }

}
