package com.rychkov.eshop.configurations;

import com.rychkov.eshop.entitys.Order;
import com.rychkov.eshop.exceptions.ReturnBooksToStockException;
import com.rychkov.eshop.repositorys.OrdersRepository;
import com.rychkov.eshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ScheduledTasks {
    private static final Integer ORDER_WINDOW_TIME = 30000;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderService orderService;

    @Async
    @Scheduled(initialDelay = 1, fixedDelay = 60000)
    public void orderWindow() {
        List<Order> orders = ordersRepository.findAllByOrderStatus_Name("TEMPORDER");
        for (Order order : orders) {
            try {
                Thread.sleep(ORDER_WINDOW_TIME);
                if (order.getOrderStatus().getName().equals("TEMPORDER")) {
                    orderService.returnBooks(order);
                    ordersRepository.delete(order);
                }
            } catch (InterruptedException | ReturnBooksToStockException e) {
                e.printStackTrace();
            }
        }
    }
}
