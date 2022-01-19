package com.adidas.publicservice.service.impl;

import com.adidas.publicservice.dto.ResponseDTO;
import com.adidas.publicservice.dto.SubscriptionDTO;
import com.adidas.publicservice.helpers.SubscriptionServiceHelper;
import com.adidas.publicservice.kafka.producer.Sender;
import com.adidas.publicservice.util.ConstantsUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
public class PublicServiceImplTest {

    @MockBean
    private Sender sender;

    private PublicServiceImpl publicService;

    @Before
    public void setup() {
        publicService = new PublicServiceImpl(sender);
    }

    @Test
    public void createSubscription_success() throws Exception {

        SubscriptionDTO subscriptionDTO = SubscriptionServiceHelper.getSubscriptionDTO();
        ResponseDTO responseDTO = publicService.createSubscription(SubscriptionServiceHelper.getSubscriptionDTO());

        assertNotNull(responseDTO);
        assertEquals(responseDTO.getResponseCode(), HttpStatus.OK);
        assertEquals(responseDTO.getResponseMessage(), ConstantsUtil.SUBSCRIPTION_OK);
    }

}
