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
    @ManyToOne(targetEntity = User.class)
    private User user;

    @NotNull
    @OneToOne(targetEntity = Address.class)
    private Address address;

    @NotNull
    @OneToOne(targetEntity = PaymentMethod.class)
    private PaymentMethod paymentMethod;

    @NotNull
    @OneToOne(targetEntity = DeliveryMethod.class)
    private DeliveryMethod deliveryMethod;

    @NotNull
    @OneToMany(targetEntity = Book.class)
    private Set<Book> books = new HashSet<>();

    @NotNull
    @OneToOne(targetEntity = PaymentStatus.class)
    private PaymentStatus paymentStatus;

    @NotNull
    @OneToOne(targetEntity = OrderStatus.class)
    private OrderStatus orderStatus;

}



