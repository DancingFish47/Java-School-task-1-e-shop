package com.rychkov.eshop.services;

import com.rychkov.eshop.entitys.Book;

import java.util.List;
import java.util.Map;

public interface BookService {
    List<Book> prepareBooksList(Map<String, String> params);
}
