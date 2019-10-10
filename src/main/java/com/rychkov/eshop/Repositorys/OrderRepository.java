package com.rychkov.eshop.Repositorys;

import com.rychkov.eshop.Entitys.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Orders, Integer> {
}
