package com.adidas.emailservice.service.impl;

import com.adidas.emailservice.dto.SubscriptionDTO;
import com.adidas.emailservice.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements IEmailService {

    @Autowired
    public JavaMailSender javaMailSender;

    public void sendEmail(SubscriptionDTO subscriptionDTO) {

        javaMailSender.send(fillEmailData(subscriptionDTO));
    }

    private SimpleMailMessage fillEmailData(SubscriptionDTO subscriptionDTO) {

        String name = subscriptionDTO.getName() != null ? subscriptionDTO.getName() : "Anonymous";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("no-reply@adidas.com");
        simpleMailMessage.setTo(subscriptionDTO.getEmail());
        simpleMailMessage.setSubject("You have subscribed to Adidas Newsletter");
        simpleMailMessage.setText("Hi " + name + "! You have subscribed successfully to Adidas Newsletter with ID - " + subscriptionDTO.getId());

        return simpleMailMessage;
    }

}
