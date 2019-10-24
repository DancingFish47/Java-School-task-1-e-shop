package com.rychkov.eshop.repositorys;

import com.rychkov.eshop.entitys.BookCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCategoryRepository extends CrudRepository<BookCategory, Integer> {

}
