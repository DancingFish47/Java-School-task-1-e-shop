package com.rychkov.eshop.repositories;

import com.rychkov.eshop.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String email);
}
