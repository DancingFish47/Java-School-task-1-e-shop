package com.rychkov.eshop.configurations;

import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.entitys.Order;
import com.rychkov.eshop.exceptions.ReturnBooksToStockException;
import com.rychkov.eshop.repositorys.BooksRepository;
import com.rychkov.eshop.repositorys.OrdersRepository;
import com.rychkov.eshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@PropertySource("classpath:properties.properties")
@Component
public class ScheduledTasks {
    private final OrdersRepository ordersRepository;
    private final OrderService orderService;
    private final RabbitTemplate rabbitTemplate;
    private final BooksRepository booksRepository;

    @Value("${scheduled.window}")
    private Integer orderWindowTime;

    @Async
    @Scheduled(initialDelay = 1, fixedDelay = 60000)
    public void orderWindow() {
        List<Order> orders = ordersRepository.findAllByOrderStatus_Name("TEMPORDER");
        for (Order order : orders) {
            try {
                Thread.sleep(orderWindowTime);
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
    @Scheduled(cron = "${scheduled.cron}")
    public void sendTopLists() {
        List<Book> topBooks = booksRepository.findTop10ByOrderBySoldDesc();
        rabbitTemplate.convertAndSend("topSellers", topBooks);
        List<Book> newBooks = booksRepository.findTop10ByOrderByDateDesc();
        rabbitTemplate.convertAndSend("newBooks", newBooks);

    }
}
