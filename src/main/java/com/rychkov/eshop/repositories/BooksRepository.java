package com.rychkov.eshop.repositories;

import com.rychkov.eshop.entities.Book;
import com.rychkov.eshop.entities.BookCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;


@Repository
public interface BooksRepository extends PagingAndSortingRepository<Book, Integer> {
    List<Book> findAllByBookCategory_Name(@NotNull String bookCategory_name);

    List<Book> findAllByBookCategory(BookCategory bookCategory);

    List<Book> findTop10ByOrderBySoldDesc();

    List<Book> findTop10ByOrderByDateDesc();

    Optional<Book> findByName(String name);

    Page<Book> findAllByBookCategory_Name(@NotNull String bookCategory_name, Pageable pageable);
}
