package com.rychkov.eshop.services;

import com.rychkov.eshop.entitys.Book;

import java.util.List;

public interface BookService {
    List<Book> sortByPrice();
    List<Book> sortByName();
}
