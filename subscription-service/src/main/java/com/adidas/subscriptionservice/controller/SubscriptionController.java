package com.adidas.subscriptionservice.controller;

import com.adidas.subscriptionservice.dto.ResponseDTO;
import com.adidas.subscriptionservice.dto.SubscriptionDTO;
import com.adidas.subscriptionservice.service.ISubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    @Autowired
    private ISubscriptionService subscriptionService;

    /**
     * Save subscription data in database and sends Kafka message to Email Service.
     *
     * @param subscriptionDTO Params received from Public Service or secured service
     * @return                Http Response
     */
    @PostMapping(value = "/subscribe")
    public ResponseEntity<?> createSubscription(
            @Valid @RequestBody SubscriptionDTO subscriptionDTO
    ) {
        ResponseDTO responseDTO = subscriptionService.createSubscription(subscriptionDTO);
        return ResponseEntity.status(responseDTO.getResponseCode()).body(subscriptionDTO);
    }

    /**
     * Change Consent property flag to false to cancel subscription
     *
     * @param id Subscription ID
     * @return   Http Response
     */
    @PutMapping(value = "/unsubscribe/{id}")
    public ResponseEntity<?> cancelSubscription(
            @Valid @PathVariable(value = "id", required = true) Integer id
    ) {
        ResponseDTO responseDTO = subscriptionService.cancelSubscription(id);
        return ResponseEntity.status(responseDTO.getResponseCode()).body(responseDTO.getResponse());
    }

    /**
     * Get subscription data from database by ID
     *
     * @param id Subscription ID
     * @return   Http Response
     */
    @GetMapping(value = "/subscription/{id}")
    public ResponseEntity<?> getSubscription(
            @Valid @PathVariable(value = "id", required = true) Integer id
    ) {
        ResponseDTO responseDTO = subscriptionService.getSubscription(id);
        return ResponseEntity.status(responseDTO.getResponseCode()).body(responseDTO.getResponse());
    }

    /**
     * Get all subscription data from database
     *
     * @return   Http Response
     */
    @GetMapping(value = "/subscriptions")
    public ResponseEntity<?> getSubscriptions() {
        ResponseDTO responseDTO = subscriptionService.getSubscriptions();
        return ResponseEntity.status(responseDTO.getResponseCode()).body(responseDTO.getResponse());
    }

}
