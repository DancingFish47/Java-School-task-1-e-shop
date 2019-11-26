package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.configurations.AppConfiguration;
import com.rychkov.eshop.configurations.PersistenceJPAConfig;
import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.dtos.NewStatusDto;
import com.rychkov.eshop.dtos.OrderAndBooks;
import com.rychkov.eshop.dtos.OrderInfoDto;
import com.rychkov.eshop.entities.*;
import com.rychkov.eshop.repositories.*;
import com.rychkov.eshop.services.OrderService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static com.rychkov.eshop.configurations.AppConfiguration.INITIAL_ORDER_STATUS;
import static com.rychkov.eshop.configurations.AppConfiguration.INITIAL_PAYMENT_STATUS;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, PersistenceJPAConfig.class})
@WebAppConfiguration
public class OrderServiceImplTest {
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    BooksRepository booksRepository;
    @Autowired
    PaymentMethodsRepository paymentMethodsRepository;
    @Autowired
    DeliveryMethodsRepository deliveryMethodsRepository;
    @Autowired
    PaymentStatusRepository paymentStatusRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressesRepository addressesRepository;
    @Autowired
    OrderService orderService;

    private List<Book> orderBooks;
    private Order order;
    private Address address;
    private User user;

    @Before
    public void setUp(){
        user = User.builder().username("TestUser").build();
        userRepository.save(user);

        orderBooks = new ArrayList<>();
        Book book = new Book();
        book.setSold(10);
        book.setAmount(10);
        book.setPrice(100f);
        orderBooks.add(book);

        booksRepository.save(book);

        OrderStatus status = new OrderStatus();
        status.setName("TEMPORDER");

        status = orderStatusRepository.save(status);

        order = new Order();
        order.setUser(user);
        order.setOrderStatus(status);
        order.setBooks(orderBooks);
        order = ordersRepository.save(order);

        status = new OrderStatus();
        status.setName(INITIAL_ORDER_STATUS);
        orderStatusRepository.save(status);

        status = new OrderStatus();
        status.setName("TESTNAME");
        orderStatusRepository.save(status);

        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setName(INITIAL_PAYMENT_STATUS);
        paymentStatusRepository.save(paymentStatus);

        paymentStatus = new PaymentStatus();
        paymentStatus.setName("TESTNAME");
        paymentStatusRepository.save(paymentStatus);

        DeliveryMethod deliveryMethod = new DeliveryMethod();
        deliveryMethod.setName("FAST");
        deliveryMethodsRepository.save(deliveryMethod);

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setName("CASH");
        paymentMethodsRepository.save(paymentMethod);

        address = Address.builder().build();
        address.setUser(user);
        address = addressesRepository.save(address);
    }

    @Test
    public void newOrder() {
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(orderBooks.get(0), 1));

        OrderInfoDto orderInfoDto = OrderInfoDto.builder()
                .addressId(address.getId())
                .deliveryMethod("FAST")
                .cartItems(cartItems)
                .paymentMethod("CASH")
                .build();

        orderService.completeOrder(orderInfoDto, order.getId());
        assertEquals("FAST", ordersRepository.findById(order.getId()).get().getDeliveryMethod().getName());
    }

    @Test
    public void findUserOrders() {
        assertNotNull(orderService.findUserOrders(user.getUsername()));
    }

    @Test
    public void findAllOrders() {
        assertNotNull(orderService.findAllOrders());
    }

    @Test
    public void repeatCartFromOrder() {
        assertNotNull(orderService.repeatCartFromOrder(order.getId()));
    }

    @Test
    public void changeOrderStatus() {
        NewStatusDto newStatusDto = new NewStatusDto();
        newStatusDto.setOrderId(order.getId());
        newStatusDto.setNewStatusName("TESTNAME");

        orderService.changeOrderStatus(newStatusDto);
        assertEquals("TESTNAME", ordersRepository.findById(order.getId()).get().getOrderStatus().getName());
    }

    @Test
    public void changePaymentStatus() {
        NewStatusDto newStatusDto = new NewStatusDto();
        newStatusDto.setOrderId(order.getId());
        newStatusDto.setNewStatusName("TESTNAME");

        orderService.changePaymentStatus(newStatusDto);
        assertEquals("TESTNAME", ordersRepository.findById(order.getId()).get().getPaymentStatus().getName());
    }

    @Test
    public void returnBooks() {
        List<OrderAndBooks> orderAndBooks = orderService.findUserOrders(user.getUsername());
        int orderAmount = orderAndBooks.get(0).getBooks().size();

        Optional<Book> optionalBook = booksRepository.findById(orderBooks.get(0).getId());
        assertTrue(optionalBook.isPresent());
        Book book = optionalBook.get();
        int currentAmount = book.getAmount();

        orderService.returnBooks(order);

        optionalBook = booksRepository.findById(orderBooks.get(0).getId());
        assertTrue(optionalBook.isPresent());
        book = optionalBook.get();
        int afterAmount = book.getAmount();

        assertEquals(afterAmount, currentAmount+orderAmount);

    }

    @After
    public void clear(){
        ordersRepository.deleteAll();
        orderStatusRepository.deleteAll();
        booksRepository.deleteAll();
        paymentMethodsRepository.deleteAll();
        deliveryMethodsRepository.deleteAll();
        paymentStatusRepository.deleteAll();
        addressesRepository.deleteAll();
        userRepository.deleteAll();
        orderBooks = null;
        order = null;
        address = null;
        user = null;
    }
}