package com.rychkov.eshop.repositorys;

import com.rychkov.eshop.entitys.DeliveryMethod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryMethodsRepository extends CrudRepository<DeliveryMethod, Integer> {
    DeliveryMethod findByName(String name);
}
