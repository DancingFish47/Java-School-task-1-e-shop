package com.rychkov.eshop.repositorys;

import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.entitys.BookCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;


@Repository
public interface BooksRepository extends PagingAndSortingRepository<Book, Integer> {
    List<Book> findAllByBookCategory_Name(@NotNull String bookCategory_name);
    List<Book> findAllByBookCategory(BookCategory bookCategory);
    List<Book> findTop10ByOrderBySoldDesc();
    List<Book> findTop10ByOrderByDateDesc();

    Page<Book> findAllByBookCategory_Name(@NotNull String bookCategory_name, Pageable pageable);
}
