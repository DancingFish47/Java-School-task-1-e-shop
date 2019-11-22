package com.rychkov.eshop.repositories;

import com.rychkov.eshop.entities.Order;
import com.rychkov.eshop.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends CrudRepository<Order, Integer> {
    List<Order> findAllByUser(User user);

    List<Order> findAllByOrderStatus_Name(String name);

    Order findFirstByOrderStatus_Name(String name);

    List<Order> findAllByAddress_Id(Integer id);
}
