package com.rychkov.eshop.repositorys;

import com.rychkov.eshop.entitys.PaymentMethod;
import org.springframework.data.repository.CrudRepository;

public interface PaymentMethodsRepository extends CrudRepository<PaymentMethod, Integer> {
}
