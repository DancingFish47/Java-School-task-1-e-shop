package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.dtos.NewStatusDto;
import com.rychkov.eshop.dtos.OrderAndBooks;
import com.rychkov.eshop.dtos.OrderInfoDto;
import com.rychkov.eshop.entitys.Order;
import com.rychkov.eshop.exceptions.FailedToChangeStatusException;
import com.rychkov.eshop.exceptions.FailedToRepeatOrderException;
import com.rychkov.eshop.exceptions.ProcessOrderException;
import com.rychkov.eshop.exceptions.ReturnBooksToStockException;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface OrderService {
    Order newOrder(OrderInfoDto orderInfoDto, HttpSession session) throws ProcessOrderException;
    List<OrderAndBooks> findUserOrders(String username);
    List<OrderAndBooks> findAllOrders();
    List<CartItem> repeatCartFromOrder(Integer orderId) throws FailedToRepeatOrderException;
    void changeOrderStatus(NewStatusDto newStatusDto) throws FailedToChangeStatusException;
    void changePaymentStatus(NewStatusDto newStatusDto) throws FailedToChangeStatusException;
    void returnBooks(Order order) throws ReturnBooksToStockException;
}
