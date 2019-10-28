package com.rychkov.eshop.configurations;

import com.rychkov.eshop.entitys.Order;
import com.rychkov.eshop.exceptions.ReturnBooksToStockException;
import com.rychkov.eshop.repositorys.OrdersRepository;
import com.rychkov.eshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class ScheduledTasks {
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderService orderService;

    private static final Integer ORDER_WINDOW_TIME = 30000;

    @Async
    @Scheduled(initialDelay = 1, fixedDelay = 60000)
    public void orderWindow(){
        Order order = ordersRepository.findFirstByOrderStatus_Name("TEMPORDER");
        try {
            Thread.sleep(ORDER_WINDOW_TIME);
            if(order.getOrderStatus().getName().equals("TEMPORDER")){
                orderService.returnBooks(order);
                ordersRepository.delete(order);
            }
        } catch (InterruptedException | ReturnBooksToStockException e) {
            e.printStackTrace();
        }
    }
}
