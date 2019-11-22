package com.rychkov.eshop.repositories;

import com.rychkov.eshop.entities.DeliveryMethod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryMethodsRepository extends CrudRepository<DeliveryMethod, Integer> {
    DeliveryMethod findByName(String name);
}
