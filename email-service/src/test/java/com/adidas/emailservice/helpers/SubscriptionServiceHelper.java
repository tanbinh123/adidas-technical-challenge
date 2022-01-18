package com.adidas.emailservice.helpers;

import com.adidas.emailservice.dto.SubscriptionDTO;

public class SubscriptionServiceHelper {

    public static SubscriptionDTO getSubscriptionDTO() {

        return new SubscriptionDTO(1, "test@test.com", "TestName", "Male", "01-01-1990", true);
    }

}
