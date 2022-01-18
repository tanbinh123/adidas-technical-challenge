package com.adidas.emailservice.service.impl;

import com.adidas.emailservice.dto.SubscriptionDTO;
import com.adidas.emailservice.helpers.SubscriptionServiceHelper;
import com.adidas.emailservice.util.ConstantsUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
public class EmailServiceTest {

    /**
     * Test fillEmailData
     *
     * @throws Exception
     */
    @Test
    public void fillEmailData_ok() throws Exception {

        SubscriptionDTO subscriptionDTO = SubscriptionServiceHelper.getSubscriptionDTO();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        String name = subscriptionDTO.getName() != null ? subscriptionDTO.getName() : ConstantsUtil.ANONYMOUS;

        simpleMailMessage.setFrom(ConstantsUtil.ADIDAS_EMAIL);
        simpleMailMessage.setTo(subscriptionDTO.getEmail());
        simpleMailMessage.setSubject(ConstantsUtil.EMAIL_SUBJECT);
        simpleMailMessage.setText(ConstantsUtil.HI + name + ConstantsUtil.EMAIL_CONTENT + subscriptionDTO.getId());

        assertNotNull(simpleMailMessage);
        assertEquals(ConstantsUtil.ADIDAS_EMAIL, simpleMailMessage.getFrom());
        assertEquals(ConstantsUtil.EMAIL_SUBJECT, simpleMailMessage.getSubject());
        assertEquals(ConstantsUtil.HI + name + ConstantsUtil.EMAIL_CONTENT + subscriptionDTO.getId(), simpleMailMessage.getText());
    }

}
