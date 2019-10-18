package com.rychkov.eshop.repositorys;

import com.rychkov.eshop.entitys.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BooksRepository extends CrudRepository<Book, Integer> {
    List<Book> findByOrderByName();
    List<Book> findByOrderByPrice();
}
