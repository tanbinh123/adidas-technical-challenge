package com.adidas.publicservice.helpers;

import com.adidas.publicservice.dto.SubscriptionDTO;

public class SubscriptionServiceHelper {

    public static SubscriptionDTO getSubscriptionDTO() {

        return new SubscriptionDTO("test@test.com", "TestName", "Male", "01-01-1990", true);
    }

}
