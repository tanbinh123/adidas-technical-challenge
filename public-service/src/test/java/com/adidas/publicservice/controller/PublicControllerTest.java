package com.adidas.publicservice.controller;

import com.adidas.publicservice.dto.ResponseDTO;
import com.adidas.publicservice.dto.SubscriptionDTO;
import com.adidas.publicservice.exception.ConstantsException;
import com.adidas.publicservice.helpers.SubscriptionServiceHelper;
import com.adidas.publicservice.service.IPublicService;
import com.adidas.publicservice.util.ConstantsUtil;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PublicController.class)
public class PublicControllerTest {

    @MockBean
    private IPublicService publicService;

    @Autowired
    private MockMvc mockMvc;

    private SubscriptionDTO subscriptionDTO = SubscriptionServiceHelper.getSubscriptionDTO();

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

        when(publicService.createSubscription(subscriptionDTO))
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

        when(publicService.createSubscription(SubscriptionServiceHelper.getSubscriptionDTO()))
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

        when(publicService.createSubscription(SubscriptionServiceHelper.getSubscriptionDTO()))
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

}
