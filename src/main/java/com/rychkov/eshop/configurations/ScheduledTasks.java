package com.rychkov.eshop.configurations;

import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.entitys.Order;
import com.rychkov.eshop.exceptions.ReturnBooksToStockException;
import com.rychkov.eshop.repositorys.BooksRepository;
import com.rychkov.eshop.repositorys.OrdersRepository;
import com.rychkov.eshop.services.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {
    private static final Integer ORDER_WINDOW_TIME = 120000;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    BooksRepository booksRepository;


    @Async
    @Scheduled(initialDelay = 1, fixedDelay = 120000)
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
                Thread.currentThread().interrupt();
            }
        }
    }
    @Async
    @Scheduled(cron = "0 * * * * *")
    public void sendTopLists(){
        List<Book> topBooks = booksRepository.findTop10ByOrderBySoldDesc();
        rabbitTemplate.convertAndSend("topSellers", topBooks);
        List<Book> newBooks = booksRepository.findTop10ByOrderByDateDesc();
        rabbitTemplate.convertAndSend("newBooks", newBooks);
        //TODO app.properties for scheduled timers
    }
}
