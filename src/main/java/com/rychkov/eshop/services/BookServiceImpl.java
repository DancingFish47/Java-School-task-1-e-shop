package com.rychkov.eshop.services;

import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.repositorys.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BooksRepository booksRepository;

    @Override
    public List<Book> sortByPrice() {
        return booksRepository.findByOrderByPrice();
    }

    @Override
    public List<Book> sortByName() {
        return booksRepository.findByOrderByName();
    }
}
