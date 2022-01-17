package com.adidas.subscriptionservice.repository;

import com.adidas.subscriptionservice.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISubscriptionRepository extends JpaRepository<Subscription, Long> {

    Subscription findById(Integer id);

}
