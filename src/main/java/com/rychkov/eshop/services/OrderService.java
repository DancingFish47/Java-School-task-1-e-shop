package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.OrderInfoDto;
import com.rychkov.eshop.entitys.Order;

public interface OrderService {
    Order newOrder(OrderInfoDto orderInfoDto);
}
