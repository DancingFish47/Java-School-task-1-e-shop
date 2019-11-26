package com.rychkov.eshop.repositories;

import com.rychkov.eshop.entities.AddressStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressStatusRepository extends CrudRepository<AddressStatus, Integer> {
    AddressStatus findByName(String name);
}
