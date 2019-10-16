package com.rychkov.eshop.repositorys;

import com.rychkov.eshop.entitys.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
}
