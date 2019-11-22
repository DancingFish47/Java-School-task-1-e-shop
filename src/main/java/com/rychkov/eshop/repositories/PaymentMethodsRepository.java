package com.rychkov.eshop.repositories;

import com.rychkov.eshop.entities.PaymentMethod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodsRepository extends CrudRepository<PaymentMethod, Integer> {
    PaymentMethod findByName(String name);
}
