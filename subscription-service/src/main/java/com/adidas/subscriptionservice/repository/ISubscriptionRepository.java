package com.adidas.subscriptionservice.repository;

import com.adidas.subscriptionservice.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * Gets subscription from database by ID
     *
     * @param id    Subscription ID
     * @return      Subscription
     */
    Subscription findById(Integer id);

}
