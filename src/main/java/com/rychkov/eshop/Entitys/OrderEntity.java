package com.rychkov.eshop.Entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@Setter
public class OrderEntity {
    public enum DeliveryMethod{
        FAST,
        SLOW,
        NODELIVERY
    }
    public enum PaymentMethod{
        CASH,
        CREDIT
    }
    public enum PaymentStatus{
        PAYED, AWAITING
    }
    public enum OrderStatus{
        PACKING, SHIPPED, DELIVERED
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private User user;
    private String customerAddress;
    private PaymentMethod paymentMethod;
    private DeliveryMethod deliveryMethod;
    private ArrayList<Book> books;
    private PaymentStatus paymentStatus;
    private OrderStatus orderStatus;

}



