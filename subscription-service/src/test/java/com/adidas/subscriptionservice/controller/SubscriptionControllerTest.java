package com.adidas.subscriptionservice.controller;

import com.adidas.subscriptionservice.dto.ResponseDTO;
import com.adidas.subscriptionservice.dto.SubscriptionDTO;
import com.adidas.subscriptionservice.exception.ConstantsException;
import com.adidas.subscriptionservice.helpers.SubscriptionServiceHelper;
import com.adidas.subscriptionservice.service.ISubscriptionService;
import com.adidas.subscriptionservice.util.ConstantsUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SubscriptionController.class)
public class SubscriptionControllerTest {

    @MockBean
    private ISubscriptionService subscriptionService;

    @Autowired
    private MockMvc mockMvc;

    private final SubscriptionDTO subscriptionDTO = SubscriptionServiceHelper.getSubscriptionDTO();

    private String requestJson;

    @Before
    public void setup() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        requestJson = ow.writeValueAsString(subscriptionDTO);
    }

    /**
     * Test createSubscription controller HttpCode 200
     */
    @Test
    public void testCreateSubscription_ok() throws Exception {

        when(subscriptionService.createSubscription(subscriptionDTO))
                .thenReturn(new ResponseDTO(HttpStatus.OK, ConstantsUtil.OK_MESSAGE));

        mockMvc.perform(post("/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    /**
     * Test createSubscription controller HttpCode 400
     */
    @Test
    public void testCreateSubscription_badRequest() throws Exception {

        when(subscriptionService.createSubscription(subscriptionDTO))
                .thenReturn(new ResponseDTO(HttpStatus.BAD_REQUEST, ConstantsException.BAD_REQUEST_MESSAGE));

        mockMvc.perform(post("/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test createSubscription controller HttpCode 500
     */
    @Test
    public void testCreateSubscription_internalError() throws Exception {

        when(subscriptionService.createSubscription(subscriptionDTO))
                .thenReturn(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, ConstantsException.INTERNAL_ERROR_MESSAGE));

        mockMvc.perform(post("/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Test createSubscription controller HttpCode 404
     */
    @Test
    public void testCreateSubscription_notFound() throws Exception {

        mockMvc.perform(post("/subscribeeeeee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }

    /**
     * Test cancelSubscription controller HttpCode 200
     */
    @Test
    public void testCancelSubscription_ok() throws Exception {

        when(subscriptionService.cancelSubscription(1))
                .thenReturn(new ResponseDTO(HttpStatus.OK, ConstantsUtil.OK_MESSAGE));

        mockMvc.perform(put("/unsubscribe/{id}", 1))
                .andExpect(status().isOk());
    }

    /**
     * Test cancelSubscription controller HttpCode 204
     */
    @Test
    public void testCancelSubscription_noContent() throws Exception {

        when(subscriptionService.cancelSubscription(1))
                .thenReturn(new ResponseDTO(HttpStatus.NO_CONTENT, ConstantsException.NO_CONTENT_MESSAGE));

        mockMvc.perform(put("/unsubscribe/{id}", 1))
                .andExpect(status().isNoContent());
    }

    /**
     * Test cancelSubscription controller HttpCode 400
     */
    @Test
    public void testCancelSubscription_badRequest() throws Exception {

        when(subscriptionService.cancelSubscription(1))
                .thenReturn(new ResponseDTO(HttpStatus.BAD_REQUEST, ConstantsException.BAD_REQUEST_MESSAGE));

        mockMvc.perform(put("/unsubscribe/{id}", 1))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test cancelSubscription controller HttpCode 500
     */
    @Test
    public void testCancelSubscription_internalError() throws Exception {

        when(subscriptionService.cancelSubscription(1))
                .thenReturn(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, ConstantsException.INTERNAL_ERROR_MESSAGE));

        mockMvc.perform(put("/unsubscribe/{id}", 1))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Test cancelSubscription controller HttpCode 404
     */
    @Test
    public void testCancelSubscription_notFound() throws Exception {

        mockMvc.perform(put("/unsuscribeasasa/{1}", 1))
                .andExpect(status().isNotFound());
    }

    /**
     * Test getSubscription controller HttpCode 200
     */
    @Test
    public void testGetSubscription_ok() throws Exception {

        when(subscriptionService.getSubscription(1))
                .thenReturn(new ResponseDTO(HttpStatus.OK, ConstantsUtil.OK_MESSAGE));

        mockMvc.perform(get("/subscription/{id}", 1))
                .andExpect(status().isOk());
    }

    /**
     * Test getSubscription controller HttpCode 204
     */
    @Test
    public void testGetSubscription_noContent() throws Exception {

        when(subscriptionService.getSubscription(1))
                .thenReturn(new ResponseDTO(HttpStatus.NO_CONTENT, ConstantsException.NO_CONTENT_MESSAGE));

        mockMvc.perform(get("/subscription/{id}", 1))
                .andExpect(status().isNoContent());
    }

    /**
     * Test getSubscription controller HttpCode 400
     */
    @Test
    public void testGetSubscription_badRequest() throws Exception {

        when(subscriptionService.getSubscription(1))
                .thenReturn(new ResponseDTO(HttpStatus.BAD_REQUEST, ConstantsException.BAD_REQUEST_MESSAGE));

        mockMvc.perform(get("/subscription/{id}", 1))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test getSubscription controller HttpCode 500
     */
    @Test
    public void testGetSubscription_internalError() throws Exception {

        when(subscriptionService.getSubscription(1))
                .thenReturn(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, ConstantsException.INTERNAL_ERROR_MESSAGE));

        mockMvc.perform(get("/subscription/{id}", 1))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Test getSubscription controller HttpCode 404
     */
    @Test
    public void testGetSubscription_notFound() throws Exception {

        mockMvc.perform(get("/subscriptionnnnnnn/{1}", 1))
                .andExpect(status().isNotFound());
    }

    /**
     * Test getSubscriptions controller HttpCode 200
     */
    @Test
    public void testGetSubscriptions_ok() throws Exception {

        when(subscriptionService.getSubscriptions())
                .thenReturn(new ResponseDTO(HttpStatus.OK, ConstantsUtil.OK_MESSAGE));

        mockMvc.perform(get("/subscriptions"))
                .andExpect(status().isOk());
    }

    /**
     * Test getSubscriptions controller HttpCode 204
     */
    @Test
    public void testGetSubscriptions_noContent() throws Exception {

        when(subscriptionService.getSubscriptions())
                .thenReturn(new ResponseDTO(HttpStatus.NO_CONTENT, ConstantsException.NO_CONTENT_MESSAGE));

        mockMvc.perform(get("/subscriptions"))
                .andExpect(status().isNoContent());
    }

    /**
     * Test getSubscriptions controller HttpCode 400
     */
    @Test
    public void testGetSubscriptions_badRequest() throws Exception {

        when(subscriptionService.getSubscriptions())
                .thenReturn(new ResponseDTO(HttpStatus.BAD_REQUEST, ConstantsException.BAD_REQUEST_MESSAGE));

        mockMvc.perform(get("/subscriptions"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test getSubscriptions controller HttpCode 500
     */
    @Test
    public void testGetSubscriptions_internalError() throws Exception {

        when(subscriptionService.getSubscriptions())
                .thenReturn(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, ConstantsException.INTERNAL_ERROR_MESSAGE));

        mockMvc.perform(get("/subscriptions"))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Test getSubscriptions controller HttpCode 404
     */
    @Test
    public void testGetSubscriptions_notFound() throws Exception {

        mockMvc.perform(get("/subscriptionsss"))
                .andExpect(status().isNotFound());
    }

}
