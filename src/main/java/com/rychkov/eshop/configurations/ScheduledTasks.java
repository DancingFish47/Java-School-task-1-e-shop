package com.rychkov.eshop.configurations;

import com.rychkov.eshop.entities.Book;
import com.rychkov.eshop.entities.Order;
import com.rychkov.eshop.exceptions.ReturnBooksToStockException;
import com.rychkov.eshop.repositories.BooksRepository;
import com.rychkov.eshop.repositories.OrdersRepository;
import com.rychkov.eshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.rychkov.eshop.configurations.RabbitConfiguration.NEW_BOOKS_QUEUE_NAME;
import static com.rychkov.eshop.configurations.RabbitConfiguration.TOP_SELLERS_QUEUE_NAME;

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
        rabbitTemplate.convertAndSend(TOP_SELLERS_QUEUE_NAME, topBooks);
        List<Book> newBooks = booksRepository.findTop10ByOrderByDateDesc();
        rabbitTemplate.convertAndSend(NEW_BOOKS_QUEUE_NAME, newBooks);
    }

    @Scheduled(initialDelay = 1000, fixedDelay=Long.MAX_VALUE)
    public void initialSend(){
        List<Book> topBooks = booksRepository.findTop10ByOrderBySoldDesc();
        rabbitTemplate.convertAndSend(TOP_SELLERS_QUEUE_NAME, topBooks);
        List<Book> newBooks = booksRepository.findTop10ByOrderByDateDesc();
        rabbitTemplate.convertAndSend(NEW_BOOKS_QUEUE_NAME, newBooks);
    }
}
