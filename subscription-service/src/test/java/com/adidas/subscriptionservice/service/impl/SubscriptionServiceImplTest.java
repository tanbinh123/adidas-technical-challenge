package com.adidas.subscriptionservice.service.impl;

import com.adidas.subscriptionservice.dto.ResponseDTO;
import com.adidas.subscriptionservice.dto.SubscriptionDTO;
import com.adidas.subscriptionservice.entity.Subscription;
import com.adidas.subscriptionservice.exception.ConstantsException;
import com.adidas.subscriptionservice.helpers.SubscriptionServiceHelper;
import com.adidas.subscriptionservice.kafka.consumer.Receiver;
import com.adidas.subscriptionservice.kafka.producer.Sender;
import com.adidas.subscriptionservice.repository.ISubscriptionRepository;
import com.adidas.subscriptionservice.util.ConstantsUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class SubscriptionServiceImplTest {

    @MockBean
    private Sender sender;

    @MockBean
    private Receiver receiver;

    @MockBean
    private ISubscriptionRepository subscriptionRepository;

    private SubscriptionServiceImpl subscriptionService;

    @Before
    public void setup() {
        subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, sender, "");
    }

    /**
     * Test createSubscription HttpCode 200
     *
     * @throws Exception
     */
    @Test
    public void createSubscription_ok() throws Exception {

        SubscriptionDTO subscriptionDTO = SubscriptionServiceHelper.getSubscriptionDTO();
        Subscription subscription = SubscriptionServiceHelper.getSubscription(true, true);

        when(subscriptionRepository.save(any(Subscription.class)))
                .thenReturn(subscription);

        ResponseDTO responseDTO = subscriptionService.createSubscription(subscriptionDTO);

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.OK, responseDTO.getResponseCode());
        assertEquals(ConstantsUtil.DATA_SAVED, responseDTO.getResponse());
    }

    /**
     * Test createSubscription HttpCode 500
     *
     * @throws Exception
     */
    @Test
    public void createSubscription_internalError() throws Exception {

        SubscriptionDTO subscriptionDTO = SubscriptionServiceHelper.getSubscriptionDTO();
        Subscription subscription = SubscriptionServiceHelper.getSubscription(false, true);

        when(subscriptionRepository.save(any(Subscription.class)))
                .thenReturn(subscription);

        ResponseDTO responseDTO = subscriptionService.createSubscription(subscriptionDTO);

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseDTO.getResponseCode());
        assertEquals(ConstantsException.ERROR_SAVING_DATA, responseDTO.getResponse());
    }

    /**
     * Test cancelSubscription HttpCode 200
     *
     * @throws Exception
     */
    @Test
    public void cancelSubscription_ok() throws Exception {

        Subscription subscription = SubscriptionServiceHelper.getSubscription(true, true);

        when(subscriptionRepository.findById(any(Integer.class))).thenReturn(subscription);
        when(subscriptionRepository.save(any(Subscription.class)))
                .thenReturn(subscription);

        ResponseDTO responseDTO = subscriptionService.cancelSubscription(1);

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.OK, responseDTO.getResponseCode());
        assertEquals(ConstantsUtil.SUBSCRIPTION_CANCELLED, responseDTO.getResponse());
    }

    /**
     * Test cancelSubscription HttpCode 200
     *
     * @throws Exception
     */
    @Test
    public void cancelSubscription_ok_alreadyCancelled() throws Exception {

        Subscription subscription = SubscriptionServiceHelper.getSubscription(true, false);

        when(subscriptionRepository.findById(any(Integer.class))).thenReturn(subscription);

        ResponseDTO responseDTO = subscriptionService.cancelSubscription(1);

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.OK, responseDTO.getResponseCode());
        assertEquals(ConstantsUtil.SUBSCRIPTION_ALREADY_CANCELLED, responseDTO.getResponse());
    }

    /**
     * Test cancelSubscription HttpCode 204
     *
     * @throws Exception
     */
    @Test
    public void cancelSubscription_noContent() throws Exception {

        when(subscriptionRepository.findById(any(Integer.class))).thenReturn(null);

        ResponseDTO responseDTO = subscriptionService.cancelSubscription(56);

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.NO_CONTENT, responseDTO.getResponseCode());
        assertEquals(ConstantsException.SUBSCRIPTION_NOT_FOUND + 56, responseDTO.getResponse());
    }

    /**
     * Test getSubscription HttpCode 200
     *
     * @throws Exception
     */
    @Test
    public void getSubscription_ok() throws Exception {

        Subscription subscription = SubscriptionServiceHelper.getSubscription(true, true);

        when(subscriptionRepository.findById(any(Integer.class))).thenReturn(subscription);

        ResponseDTO responseDTO = subscriptionService.getSubscription(1);

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.OK, responseDTO.getResponseCode());
        assertEquals(subscription, responseDTO.getResponse());
    }

    /**
     * Test getSubscription HttpCode 204
     *
     * @throws Exception
     */
    @Test
    public void getSubscription_noContent() throws Exception {

        Subscription subscription = SubscriptionServiceHelper.getSubscription(true, true);

        when(subscriptionRepository.findById(any(Integer.class))).thenReturn(null);

        ResponseDTO responseDTO = subscriptionService.getSubscription(15);

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.NO_CONTENT, responseDTO.getResponseCode());
        assertEquals(ConstantsException.SUBSCRIPTION_NOT_FOUND + 15, responseDTO.getResponse());
    }

    /**
     * Test getSubscriptions HttpCode 200
     *
     * @throws Exception
     */
    @Test
    public void getSubscriptions_ok() throws Exception {

        List<Subscription> subscriptionList = new ArrayList<>();
        subscriptionList.add(SubscriptionServiceHelper.getSubscription(true, true));

        when(subscriptionRepository.findAll()).thenReturn(subscriptionList);

        ResponseDTO responseDTO = subscriptionService.getSubscriptions();

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.OK, responseDTO.getResponseCode());
        assertEquals(subscriptionList, responseDTO.getResponse());
    }

    /**
     * Test getSubscriptions HttpCode 204
     *
     * @throws Exception
     */
    @Test
    public void getSubscriptions_noContent() throws Exception {

        when(subscriptionRepository.findAll()).thenReturn(null);

        ResponseDTO responseDTO = subscriptionService.getSubscriptions();

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.NO_CONTENT, responseDTO.getResponseCode());
        assertEquals(ConstantsException.SUBSCRIPTIONS_NOT_FOUND, responseDTO.getResponse());
    }

}
