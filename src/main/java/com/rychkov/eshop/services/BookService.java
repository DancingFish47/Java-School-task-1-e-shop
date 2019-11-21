package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.BookDto;
import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.exceptions.BookException;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface BookService {
    Page<Book> prepareBooksList(Map<String, String> params);

    void deleteBookById(Integer bookId) throws BookException;

    void addNewBook(BookDto bookDto) throws BookException;

    void editBook(BookDto bookDto) throws BookException;
}
