package com.rychkov.eshop.Repositorys;

import com.rychkov.eshop.Entitys.Products;
import org.springframework.data.repository.CrudRepository;

public interface ProductsRepository extends CrudRepository<Products, Integer> {
}
