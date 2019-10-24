package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.OrderInfoDto;
import com.rychkov.eshop.entitys.Order;
import com.rychkov.eshop.exceptions.ProcessOrderException;

public interface OrderService {
    Order newOrder(OrderInfoDto orderInfoDto) throws ProcessOrderException;
}
