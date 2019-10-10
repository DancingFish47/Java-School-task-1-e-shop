package com.rychkov.eshop.Repositorys;

import com.rychkov.eshop.Entitys.Users;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Users, Integer> {
}
