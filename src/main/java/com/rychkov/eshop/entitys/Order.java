package com.rychkov.eshop.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Orders")
public class Order extends AbstractEntity {
    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="address_id")
    private Address address;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="paymentMethod_id")
    private PaymentMethod paymentMethod;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="deliveryMethod_id")
    private DeliveryMethod deliveryMethod;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Order_Books",
            joinColumns = { @JoinColumn(name = "order_id") },
            inverseJoinColumns = { @JoinColumn(name = "book_id") }
    )
    Set<Book> books = new HashSet<>();

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="paymentStatus_id")
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="orderStatus_id")
    private OrderStatus orderStatus;

}



