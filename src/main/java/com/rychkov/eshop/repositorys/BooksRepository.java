package com.rychkov.eshop.repositorys;

import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.entitys.BookCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;


@Repository
public interface BooksRepository extends CrudRepository<Book, Integer> {
    List<Book> findByOrderByPrice();
    List<Book> findByOrderByName();
    List<Book> findAllByBookCategory_Name(@NotNull String bookCategory_name);
    List<Book> findAllByBookCategory_NameOrderByPrice(@NotNull String bookCategory_name);
    List<Book> findAllByBookCategory_NameOrderByName(@NotNull String bookCategory_name);

}
