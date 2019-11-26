package com.rychkov.eshop.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order extends AbstractEntity {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_books",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    List<Book> books;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentMethod_id")
    private PaymentMethod paymentMethod;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deliveryMethod_id")
    private DeliveryMethod deliveryMethod;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentStatus_id")
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderStatus_id")
    private OrderStatus orderStatus;

    @Column(name = "totalPrice")
    private Float totalPrice;

}



