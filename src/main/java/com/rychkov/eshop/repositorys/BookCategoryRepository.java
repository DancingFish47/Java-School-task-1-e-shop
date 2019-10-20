package com.rychkov.eshop.repositorys;

import com.rychkov.eshop.entitys.Book;
import com.rychkov.eshop.entitys.BookCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookCategoryRepository extends CrudRepository<BookCategory, Integer> {

}
