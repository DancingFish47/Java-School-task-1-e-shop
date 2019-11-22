package com.rychkov.eshop.repositories;

import com.rychkov.eshop.entities.BookCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCategoryRepository extends CrudRepository<BookCategory, Integer> {
    BookCategory findByName(String name);
}
