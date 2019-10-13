package com.rychkov.eshop.Repositorys;

import com.rychkov.eshop.Entitys.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
