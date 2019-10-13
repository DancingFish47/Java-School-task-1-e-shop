package com.rychkov.eshop.Repositorys;

import com.rychkov.eshop.Entitys.Book;
import org.springframework.data.repository.CrudRepository;

public interface BooksRepository extends CrudRepository<Book, Integer> {
}
