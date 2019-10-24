package com.rychkov.eshop.repositorys;

import com.rychkov.eshop.entitys.OrderStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends CrudRepository<OrderStatus, Integer> {
    OrderStatus findByName(String name);
}
