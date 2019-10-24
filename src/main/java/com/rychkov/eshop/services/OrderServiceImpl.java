package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.CartItem;
import com.rychkov.eshop.dtos.OrderInfoDto;
import com.rychkov.eshop.entitys.*;
import com.rychkov.eshop.exceptions.ProcessOrderException;
import com.rychkov.eshop.repositorys.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    public static final String INITIAL_ORDER_STATUS = "REGISTERED";
    public static final String INITIAL_PAYMENT_STATUS = "NOT PAYED";

    @Autowired
    BooksRepository booksRepository;
    @Autowired
    PaymentMethodsRepository paymentMethodsRepository;
    @Autowired
    DeliveryMethodsRepository deliveryMethodsRepository;
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    PaymentStatusRepository paymentStatusRepository;
    @Autowired
    AddressesRepository addressesRepository;
    @Override
    @Transactional
    public Order newOrder(OrderInfoDto orderInfoDto) throws ProcessOrderException {
        Order order = new Order();
        float totalPrice = 0;
        List<Book> orderBooks = new ArrayList<>();
        //CHECKING BOOKS QUANTITY AND ADDING BOOKS TO ORDER
        for (CartItem item : orderInfoDto.getCartItems()){
            Optional<Book> optionalBook = booksRepository.findById(item.getBook().getId());
            if(optionalBook.isPresent()){
                Book book = optionalBook.get();
                if (book.getAmount()<item.getQuantity()) throw new ProcessOrderException("Book " + item.getBook().getName() + " is out of stock");
                else {
                    int newAmount = book.getAmount() - item.getQuantity();
                    book.setAmount(newAmount);
                    booksRepository.save(book);
                    for(int i = 0; i<item.getQuantity(); i++){
                        orderBooks.add(item.getBook());
                    }
                    totalPrice+= item.getBook().getPrice()*item.getQuantity();
                }
            }else{
                throw new ProcessOrderException("Book " + item.getBook().getName() + " is not found");
            }
        }
        order.setBooks(orderBooks);

        Optional<Address> optionalAddress = addressesRepository.findById(orderInfoDto.getAddressId());
        if(optionalAddress.isPresent()) order.setAddress(optionalAddress.get());
        else throw new ProcessOrderException("Address not found");

        DeliveryMethod deliveryMethod = deliveryMethodsRepository.findByName(orderInfoDto.getDeliveryMethod());
        if (deliveryMethod != null) order.setDeliveryMethod(deliveryMethod);
        else throw new ProcessOrderException("Delivery method not found");

        PaymentMethod paymentMethod = paymentMethodsRepository.findByName(orderInfoDto.getPaymentMethod());
        if (paymentMethod != null) order.setPaymentMethod(paymentMethod);
        else throw new ProcessOrderException("Payment method not found");

        OrderStatus orderStatus = orderStatusRepository.findByName(INITIAL_ORDER_STATUS);
        if(orderStatus != null) order.setOrderStatus(orderStatus);
        else throw new ProcessOrderException("Order status error");

        PaymentStatus paymentStatus = paymentStatusRepository.findByName(INITIAL_PAYMENT_STATUS);
        if(paymentStatus != null) order.setPaymentStatus(paymentStatus);
        else throw new ProcessOrderException("Payment status error");


        order.setUser(orderInfoDto.getUser());
        order.setTotalPrice(totalPrice);
        return ordersRepository.save(order);
    }
}
/*
TODO EXCEPTION HANDLING,
  CONTROLLER_ADVICE,
   ADMIN PAGE,
    OUTOFSTOCK EXCEPTION
    ERRORPAGES
 */