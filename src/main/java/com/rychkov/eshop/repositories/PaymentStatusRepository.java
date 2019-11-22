package com.rychkov.eshop.repositories;

import com.rychkov.eshop.entities.PaymentStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentStatusRepository extends CrudRepository<PaymentStatus, Integer> {
    PaymentStatus findByName(String name);
}
