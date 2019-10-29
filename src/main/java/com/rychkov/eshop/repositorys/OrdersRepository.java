package com.rychkov.eshop.repositorys;

import com.rychkov.eshop.entitys.Order;
import com.rychkov.eshop.entitys.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends CrudRepository<Order, Integer> {
    List<Order> findAllByUser(User user);

    Order findFirstByOrderStatus_Name(String name);
}
