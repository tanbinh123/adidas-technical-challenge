package com.adidas.subscriptionservice.controller;

import com.adidas.subscriptionservice.dto.ResponseDTO;
import com.adidas.subscriptionservice.dto.SubscriptionDTO;
import com.adidas.subscriptionservice.service.ISubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    @Autowired
    private ISubscriptionService subscriptionService;

    @PostMapping(value = "/subscribe")
    public ResponseEntity<SubscriptionDTO> createSubscription(
            @Valid SubscriptionDTO subscriptionDTO
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(subscriptionDTO);
    }

    @PostMapping(value = "/unsubscribe/{id}")
    public ResponseEntity<String> cancelSubscription(
            @Valid @PathVariable(value = "id", required = true) Integer id
    ) {

        return ResponseEntity.status(HttpStatus.OK).body("ID: " + id);
    }

    @GetMapping(value = "/subscription/{id}")
    public ResponseEntity<?> getSubscription(
            @Valid @PathVariable(value = "id", required = true) BigInteger id
    ) {
        ResponseDTO responseDTO = subscriptionService.getSubscription(id);
        return ResponseEntity.status(responseDTO.getResponseCode()).body(responseDTO.getResponse());
    }

    @GetMapping(value = "/subscriptions")
    public ResponseEntity<?> getSubscriptions() {
        ResponseDTO responseDTO = subscriptionService.getSubscriptions();
        return ResponseEntity.status(responseDTO.getResponseCode()).body(responseDTO.getResponse());
    }

}
