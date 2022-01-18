package com.adidas.subscriptionservice.helpers;

import com.adidas.subscriptionservice.dto.SubscriptionDTO;
import com.adidas.subscriptionservice.entity.Subscription;

public class SubscriptionServiceHelper {

    public static SubscriptionDTO getSubscriptionDTO() {

        return new SubscriptionDTO(null, "test@test.com", "TestName", "Male", "01-01-1990", true);
    }

    public static Subscription getSubscription(Boolean setId, Boolean setConsent) {

        return new Subscription(setId == false ? null : 1, "test@test.com", "TestName", "Male",
                "01-01-1990", setConsent == false ? false: true);
    }

}