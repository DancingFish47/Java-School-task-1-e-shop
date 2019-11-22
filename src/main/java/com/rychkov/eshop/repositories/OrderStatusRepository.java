package com.rychkov.eshop.repositories;

import com.rychkov.eshop.entities.OrderStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends CrudRepository<OrderStatus, Integer> {
    OrderStatus findByName(String name);
}
