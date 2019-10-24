package com.rychkov.eshop.repositorys;

import com.rychkov.eshop.entitys.PaymentMethod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodsRepository extends CrudRepository<PaymentMethod, Integer> {
    PaymentMethod findByName(String name);
}
