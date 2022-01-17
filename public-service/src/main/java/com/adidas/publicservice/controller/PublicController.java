package com.adidas.publicservice.controller;

import com.adidas.publicservice.dto.SubscriptionDTO;
import com.adidas.publicservice.kafka.producer.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@Component
public class PublicController {

    @Value("${spring.kafka.topic.subscribe}")
    private String SUBSCRIBE_TOPIC;

    private Sender sender;

    @Autowired
    PublicController(Sender sender) {
        this.sender = sender;
    }

    @PostMapping(value = "/subscribe")
    public ResponseEntity<SubscriptionDTO> createSubscription(
            @Valid SubscriptionDTO subscriptionDTO
    ) {
        sender.send(SUBSCRIBE_TOPIC, subscriptionDTO);
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionDTO);
    }

}