package com.adidas.emailservice.service.impl;

import com.adidas.emailservice.dto.SubscriptionDTO;
import com.adidas.emailservice.service.IEmailService;
import com.adidas.emailservice.util.ConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements IEmailService {

    @Autowired
    public JavaMailSender javaMailSender;

    /**
     * Method that sends email to the subscriber
     *
     * @param subscriptionDTO   JSON received from Subscription Service
     */
    public void sendEmail(SubscriptionDTO subscriptionDTO) {

        javaMailSender.send(fillEmailData(subscriptionDTO));
    }

    /**
     * Method that fills email content
     *
     * @param subscriptionDTO   JSON received from Subscription Service
     * @return
     */
    private SimpleMailMessage fillEmailData(SubscriptionDTO subscriptionDTO) {

        String name = subscriptionDTO.getName() != null ? subscriptionDTO.getName() : ConstantsUtil.ANONYMOUS;

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(ConstantsUtil.ADIDAS_EMAIL);
        simpleMailMessage.setTo(subscriptionDTO.getEmail());
        simpleMailMessage.setSubject(ConstantsUtil.EMAIL_SUBJECT);
        simpleMailMessage.setText(ConstantsUtil.HI + name + ConstantsUtil.EMAIL_CONTENT + subscriptionDTO.getId());

        return simpleMailMessage;
    }

}
