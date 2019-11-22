package com.rychkov.eshop.services.implementations;

import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.dtos.NewStatusDto;
import com.rychkov.eshop.dtos.OrderAndBooks;
import com.rychkov.eshop.dtos.OrderInfoDto;
import com.rychkov.eshop.entities.*;
import com.rychkov.eshop.exceptions.FailedToChangeStatusException;
import com.rychkov.eshop.exceptions.FailedToRepeatOrderException;
import com.rychkov.eshop.exceptions.ProcessOrderException;
import com.rychkov.eshop.exceptions.ReturnBooksToStockException;
import com.rychkov.eshop.repositories.*;
import com.rychkov.eshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    public static final String INITIAL_ORDER_STATUS = "REGISTERED";
    public static final String INITIAL_PAYMENT_STATUS = "NOT PAYED";

    private final BooksRepository booksRepository;
    private final PaymentMethodsRepository paymentMethodsRepository;
    private final DeliveryMethodsRepository deliveryMethodsRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrdersRepository ordersRepository;
    private final PaymentStatusRepository paymentStatusRepository;
    private final AddressesRepository addressesRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void completeOrder(OrderInfoDto orderInfoDto, Integer orderId) throws ProcessOrderException {
        Optional<Order> optionalOrder = ordersRepository.findById(orderId);
        if (!optionalOrder.isPresent()) throw new ProcessOrderException("Failed to process order");
        Order order = optionalOrder.get();
        float totalPrice = 0;

        for (CartItem item : orderInfoDto.getCartItems()) {
            Optional<Book> optionalBook = booksRepository.findById(item.getBook().getId());
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                int newSold = book.getSold() + item.getQuantity();
                book.setSold(newSold);
                booksRepository.save(book);
                totalPrice += item.getBook().getPrice() * item.getQuantity();
            } else {
                throw new ProcessOrderException("Book " + item.getBook().getName() + " is not found");
            }
        }

        Optional<Address> optionalAddress = addressesRepository.findById(orderInfoDto.getAddressId());
        if (optionalAddress.isPresent()) order.setAddress(optionalAddress.get());
        else throw new ProcessOrderException("Address not found");

        DeliveryMethod deliveryMethod = deliveryMethodsRepository.findByName(orderInfoDto.getDeliveryMethod());
        if (deliveryMethod != null) order.setDeliveryMethod(deliveryMethod);
        else throw new ProcessOrderException("Delivery method not found");

        PaymentMethod paymentMethod = paymentMethodsRepository.findByName(orderInfoDto.getPaymentMethod());
        if (paymentMethod != null) order.setPaymentMethod(paymentMethod);
        else throw new ProcessOrderException("Payment method not found");

        OrderStatus orderStatus = orderStatusRepository.findByName(INITIAL_ORDER_STATUS);
        if (orderStatus != null) order.setOrderStatus(orderStatus);
        else throw new ProcessOrderException("Order status error");

        PaymentStatus paymentStatus = paymentStatusRepository.findByName(INITIAL_PAYMENT_STATUS);
        if (paymentStatus != null) order.setPaymentStatus(paymentStatus);
        else throw new ProcessOrderException("Payment status error");

        order.setUser(orderInfoDto.getUser());
        order.setTotalPrice(totalPrice);

        ordersRepository.save(order);
    }

    @Override
    public List<OrderAndBooks> findUserOrders(String username) {
        List<OrderAndBooks> orderAndBooks = new ArrayList<>();
        List<Order> orderList = ordersRepository.findAllByUser(userRepository.findByUsername(username));
        return getOrderAndBooks(orderAndBooks, orderList);
    }

    @Override
    public List<OrderAndBooks> findAllOrders() {
        List<OrderAndBooks> orderAndBooks = new ArrayList<>();
        List<Order> orderList = (List<Order>) ordersRepository.findAll();
        return getOrderAndBooks(orderAndBooks, orderList);
    }

    @Override
    public List<CartItem> repeatCartFromOrder(Integer orderId) throws FailedToRepeatOrderException {
        List<CartItem> repeatCart = new ArrayList<>();

        Optional<Order> optionalOrder = ordersRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            for (Book book : order.getBooks()) {
                if (exists(book.getId(), repeatCart) == -1) {
                    repeatCart.add(new CartItem(book, 1));
                } else {
                    repeatCart.get(exists(book.getId(), repeatCart)).addQuantity(1);
                }
            }
        } else throw new FailedToRepeatOrderException("Cant find that order in DB");

        return repeatCart;
    }

    @Override
    public void changeOrderStatus(NewStatusDto newStatusDto) throws FailedToChangeStatusException {
        Optional<Order> optionalOrder = ordersRepository.findById(newStatusDto.getOrderId());
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderStatus(orderStatusRepository.findByName(newStatusDto.getNewStatusName()));
            ordersRepository.save(order);
        } else {
            throw new FailedToChangeStatusException("Order with that id is not found");
        }
    }

    @Override
    public void changePaymentStatus(NewStatusDto newStatusDto) throws FailedToChangeStatusException {
        Optional<Order> optionalOrder = ordersRepository.findById(newStatusDto.getOrderId());
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setPaymentStatus(paymentStatusRepository.findByName(newStatusDto.getNewStatusName()));
            ordersRepository.save(order);
        } else {
            throw new FailedToChangeStatusException("Order with that id is not found");
        }
    }

    @Transactional
    @Override
    public void returnBooks(Order order) throws ReturnBooksToStockException {
        for (Book book : order.getBooks()) {
            Optional<Book> optionalBook = booksRepository.findById(book.getId());
            if (optionalBook.isPresent()) {
                Book b = optionalBook.get();
                b.setAmount(b.getAmount() + 1);
            } else throw new ReturnBooksToStockException("Failed to return books to stocks");
        }
    }

    private List<OrderAndBooks> getOrderAndBooks(List<OrderAndBooks> orderAndBooks, List<Order> orderList) {
        for (Order order : orderList) {
            List<CartItem> bookList = new ArrayList<>();
            for (Book book : order.getBooks()) {
                if (exists(book.getId(), bookList) == -1) {
                    bookList.add(new CartItem(book, 1));
                } else {
                    bookList.get(exists(book.getId(), bookList)).addQuantity(1);
                }
            }
            orderAndBooks.add(new OrderAndBooks(order, bookList));
        }
        return orderAndBooks;
    }


    private int exists(Integer id, List<CartItem> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getBook().getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
