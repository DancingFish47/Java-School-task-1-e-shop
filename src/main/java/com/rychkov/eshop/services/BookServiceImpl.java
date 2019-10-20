package com.rychkov.eshop.services;

import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.repositorys.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BooksRepository booksRepository;


    @Override
    public List<Book> prepareBooksList(Map<String, String> params) {
        List<Book> preparedBooks;
        if(params.get("category") == null) preparedBooks = (List<Book>) booksRepository.findAll();
        else preparedBooks = booksRepository.findAllByBookCategory_Name(params.get("category"));

        if(params.get("sortType") == null) return preparedBooks;
        else {
            switch (params.get("sortType")){
                case "price":
                    preparedBooks.sort((o1, o2) -> {
                        if (o1.getPrice()>o2.getPrice()) return 1;
                        if (o1.getPrice().equals(o2.getPrice())) return 0;
                        if (o1.getPrice()<o2.getPrice()) return -1;
                        return 0;
                    });
                    break;
                case "name":
                    preparedBooks.sort(Comparator.comparing(Book::getName));
                    break;
                default:
                    break;
            }

        }
        return preparedBooks;
    }
}
