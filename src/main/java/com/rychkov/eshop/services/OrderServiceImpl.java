package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.OrderInfoDto;
import com.rychkov.eshop.entitys.Order;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    @Transactional
    public Order newOrder(OrderInfoDto orderInfoDto) {

        return null;
    }
}
/*
TODO EXCEPTION HANDLING,
  CONTROLLER_ADVICE,
  MAPPER SEEMS IRRELEVANT AT THIS POINT,
   ADMIN PAGE,
    YOUR ORDERS PAGE,
     REPEAT ORDER,
    newOrder METHOD WITH TRANSACTION
    OUTOFSTOCK EXCEPTION
    ERRORPAGES
 */