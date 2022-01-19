package com.adidas.subscriptionservice.service.impl;

import com.adidas.subscriptionservice.dto.ResponseDTO;
import com.adidas.subscriptionservice.dto.SubscriptionDTO;
import com.adidas.subscriptionservice.entity.Subscription;
import com.adidas.subscriptionservice.exception.ConstantsException;
import com.adidas.subscriptionservice.kafka.producer.Sender;
import com.adidas.subscriptionservice.repository.ISubscriptionRepository;
import com.adidas.subscriptionservice.service.ISubscriptionService;
import com.adidas.subscriptionservice.util.ConstantsUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@AllArgsConstructor
public class SubscriptionServiceImpl implements ISubscriptionService {

    @Autowired
    private ISubscriptionRepository subscriptionRepository;

    private Sender sender;

    @Autowired
    SubscriptionServiceImpl(Sender sender) {
        this.sender = sender;
    }

    @Value("${spring.kafka.topic.sendEmail}")
    private String SEND_EMAIL_TOPIC;

    /**
     * Save subscription data in database and sends Kafka message to Email Service.
     *
     * @param subscriptionDTO Params received from Public Service or secured service
     * @return                Http Response
     */
    public ResponseDTO createSubscription(SubscriptionDTO subscriptionDTO) {

        Subscription subscription = new Subscription(null, subscriptionDTO.getEmail(), subscriptionDTO.getName(), subscriptionDTO.getGender(),
                subscriptionDTO.getBirthdate(), subscriptionDTO.getConsent());

        subscriptionDTO.setId(saveSubscription(subscription).getId());

        if (subscriptionDTO.getId() != null) {
            // Send SubscriptionDTO to Kafka Server for sending email
            sender.send(SEND_EMAIL_TOPIC, subscriptionDTO);
            return new ResponseDTO(HttpStatus.OK, ConstantsUtil.DATA_SAVED);
        } else {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, ConstantsException.ERROR_SAVING_DATA);
        }

    }

    /**
     * Change consent property flag to false for canceling subscription
     *
     * @param id    Subscription ID
     * @return      Http Response
     */
    public ResponseDTO cancelSubscription(Integer id) {

        Subscription subscription = getSubscriptionById(id);

        if (subscription != null && subscription.getConsent()) {
            subscription.setConsent(false);
            saveSubscription(subscription);

            return new ResponseDTO(HttpStatus.OK, ConstantsUtil.SUBSCRIPTION_CANCELLED);
        } else if (subscription != null && !subscription.getConsent()) {
            return new ResponseDTO(HttpStatus.OK, ConstantsUtil.SUBSCRIPTION_ALREADY_CANCELLED);
        } else {
            return new ResponseDTO(HttpStatus.NO_CONTENT, ConstantsException.SUBSCRIPTION_NOT_FOUND + id);
        }

    }

    /**
     * Get subscription data from database by ID
     *
     * @param id    Subscription ID
     * @return      Http Response
     */
    public ResponseDTO getSubscription(Integer id) {

        Subscription subscription = getSubscriptionById(id);

        if (subscription != null) {
            return new ResponseDTO(HttpStatus.OK, subscription);
        } else {
            return new ResponseDTO(HttpStatus.NO_CONTENT, ConstantsException.SUBSCRIPTION_NOT_FOUND + id);
        }

    }

    /**
     * Get all subscription data from database
     *
     * @return      Http Response
     */
    public ResponseDTO getSubscriptions() {

        List<Subscription> subscriptionList = getAllSubscriptions();

        if (subscriptionList != null && !subscriptionList.isEmpty()) {
            return new ResponseDTO(HttpStatus.OK, subscriptionList);
        } else {
            return new ResponseDTO(HttpStatus.NO_CONTENT, ConstantsException.SUBSCRIPTIONS_NOT_FOUND);
        }

    }

    /**
     * Saves new subscription in database
     *
     * @param subscription  Subscription to save
     * @return              Subscription saved (with ID)
     */
    private Subscription saveSubscription(Subscription subscription) {

        return subscriptionRepository.save(subscription);
    }

    /**
     * Retrieves Subscription object from database by ID
     *
     * @param id    Subscription ID
     * @return      Subscription
     */
    private Subscription getSubscriptionById(Integer id) {

        return subscriptionRepository.findById(id);
    }

    /**
     * Retrieves all Subscription objects from database
     *
     * @return      List<Subscription>
     */
    private List<Subscription> getAllSubscriptions() {

        return subscriptionRepository.findAll();
    }

}
