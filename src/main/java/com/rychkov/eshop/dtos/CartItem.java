package com.rychkov.eshop.dtos;

import com.rychkov.eshop.entitys.Book;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@EqualsAndHashCode
public class CartItem {
    private Book book;
    private Integer quantity;
    public CartItem(Book book, Integer quantity){
        this.book = book;
        this.quantity = quantity;
    }
    public void addQuantity(Integer add){
        quantity = quantity + add;
    }

}
