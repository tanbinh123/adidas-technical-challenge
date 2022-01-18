package com.adidas.publicservice.controller;

import com.adidas.publicservice.dto.ResponseDTO;
import com.adidas.publicservice.dto.SubscriptionDTO;
import com.adidas.publicservice.service.IPublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RequiredArgsConstructor
@RestController
public class PublicController {

    @Autowired
    IPublicService publicService;

    /**
     * Sends subscription data to Kafka server to subscribe the user in Subscription Service.
     *
     * @param subscriptionDTO Params received from the UI frontend
     * @return                Http Response
     */
    @PostMapping(value = "/subscribe")
    public ResponseEntity<?> createSubscription(
            @Valid @RequestBody SubscriptionDTO subscriptionDTO
    ) {
        ResponseDTO responseDTO = publicService.createSubscription(subscriptionDTO);
        return ResponseEntity.status(responseDTO.getResponseCode()).body(responseDTO.getResponseMessage());
    }

}