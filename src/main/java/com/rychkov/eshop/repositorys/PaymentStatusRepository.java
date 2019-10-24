package com.rychkov.eshop.repositorys;

import com.rychkov.eshop.entitys.PaymentStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentStatusRepository extends CrudRepository<PaymentStatus, Integer> {
    PaymentStatus findByName(String name);
}
