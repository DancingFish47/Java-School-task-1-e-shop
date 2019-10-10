package com.rychkov.eshop.Entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;

@Entity
@Getter
@Setter
public class Orders {
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
    private Integer userId;
    private String customerAddress;
    private PaymentMethod paymentMethod;
    private DeliveryMethod deliveryMethod;
    private ArrayList<Products> products;
    private PaymentStatus paymentStatus;
    private OrderStatus orderStatus;

}



