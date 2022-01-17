package com.adidas.subscriptionservice.service.impl;

import com.adidas.subscriptionservice.dto.ResponseDTO;
import com.adidas.subscriptionservice.dto.SubscriptionDTO;
import com.adidas.subscriptionservice.entity.Subscription;
import com.adidas.subscriptionservice.kafka.producer.Sender;
import com.adidas.subscriptionservice.repository.ISubscriptionRepository;
import com.adidas.subscriptionservice.service.ISubscriptionService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@AllArgsConstructor
public class SubscriptionServiceImpl implements ISubscriptionService {


    @Value("${spring.kafka.topic.sendEmail}")
    private String SEND_EMAIL_TOPIC;

    private Sender sender;

    @Autowired
    SubscriptionServiceImpl(Sender sender) {
        this.sender = sender;
    }

    @Autowired
    private ISubscriptionRepository subscriptionRepository;

    public ResponseDTO createSubscription(SubscriptionDTO subscriptionDTO) {

        ResponseDTO responseDTO = new ResponseDTO();

        Subscription subscription = new Subscription();
        subscription.setEmail(subscriptionDTO.getEmail());
        subscription.setName(subscriptionDTO.getName());
        subscription.setGender(subscriptionDTO.getGender());
        subscription.setBirthdate(new Timestamp(subscriptionDTO.getBirthdate().getTime()));
        subscription.setConsent(subscriptionDTO.getConsent());

        subscriptionDTO.setId(subscriptionRepository.save(subscription).getId());

        if (subscriptionDTO.getId() != null) {
            responseDTO.setResponseCode(HttpStatus.OK);
            responseDTO.setResponse(subscription.toString());
            sender.send(SEND_EMAIL_TOPIC, subscriptionDTO);
        } else {
            responseDTO.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDTO.setResponse("Error");
        }

        return responseDTO;
    }

    public ResponseDTO cancelSubscription(Integer id) {

        ResponseDTO responseDTO = new ResponseDTO();
        Subscription subscription = getSubscriptionById(id);

        if (subscription != null) {
            if (subscription.getConsent()) {
                subscription.setConsent(false);
                responseDTO.setResponseCode(HttpStatus.OK);
                responseDTO.setResponse(subscription.toString());
                subscriptionRepository.save(subscription);
            } else {
                responseDTO.setResponseCode(HttpStatus.OK);
                responseDTO.setResponse("Already cancelled");
            }
        } else {
            responseDTO.setResponseCode(HttpStatus.NO_CONTENT);
            responseDTO.setResponse("Invalid subscription ID");
        }

        return responseDTO;
    }

    public ResponseDTO getSubscription(Integer id) {

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

    private Subscription getSubscriptionById(Integer id) {

        return subscriptionRepository.findById(id);
    }

    private List<Subscription> getAllSubscriptions() {

        return subscriptionRepository.findAll();
    }

}
