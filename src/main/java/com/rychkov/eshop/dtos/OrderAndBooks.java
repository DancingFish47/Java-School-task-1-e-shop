package com.rychkov.eshop.dtos;

import com.rychkov.eshop.entities.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrderAndBooks {
    private Order order;
    private List<CartItem> books;

    public OrderAndBooks(Order order, List<CartItem> books) {
        this.order = order;
        this.books = books;
    }
}