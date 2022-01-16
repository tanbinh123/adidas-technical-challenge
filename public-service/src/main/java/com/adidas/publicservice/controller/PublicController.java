package com.adidas.publicservice.controller;

import com.adidas.publicservice.dto.SubscriptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class PublicController {

    @PostMapping(value = "/subscribe")
    public ResponseEntity<SubscriptionDTO> createSubscription(
            @Valid @RequestParam(name = "email", required = true) String email,
            @Valid @RequestParam(name = "name", required = false) String name,
            @Valid @RequestParam(name = "gender", required = false) String gender,
            @Valid @RequestParam(name = "birthdate", required = true) @DateTimeFormat(pattern="yyyy-MM-dd") Date birthdate,
            @Valid @RequestParam(name = "consent", required = true) Boolean consent
    ) {

        SubscriptionDTO subscriptionDTO = new SubscriptionDTO(email, name, gender, birthdate, consent);

        return ResponseEntity.status(HttpStatus.OK).body(subscriptionDTO);
    }

    @DeleteMapping(value = "/unsubscribe/{id}")
    public ResponseEntity<String> deleteSubscription(
            @Valid @PathVariable(value = "id", required = true) Integer id
    ) {

        return ResponseEntity.status(HttpStatus.OK).body("ID: " + id);
    }

    @GetMapping(value = "/subscription/{id}")
    public ResponseEntity<String> getSubscription(
            @Valid @PathVariable(value = "id", required = true) Integer id
    ) {

        return ResponseEntity.status(HttpStatus.OK).body("ID: " + id);
    }

    @GetMapping(value = "/subscriptions")
    public ResponseEntity<String> getAllSubscriptions() {

        return ResponseEntity.status(HttpStatus.OK).body("Testing getAllSubscriptions Method");
    }

}