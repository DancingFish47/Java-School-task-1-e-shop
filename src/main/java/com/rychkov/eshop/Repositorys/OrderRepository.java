package com.rychkov.eshop.Repositorys;

import com.rychkov.eshop.Entitys.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {
}
