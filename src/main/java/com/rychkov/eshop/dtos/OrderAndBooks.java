package com.rychkov.eshop.dtos;

import com.rychkov.eshop.entitys.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderAndBooks {
    private Order order;
    private List<CartItem> books;

    public OrderAndBooks(Order order, List<CartItem> books) {
        this.order = order;
        this.books = books;
    }
}