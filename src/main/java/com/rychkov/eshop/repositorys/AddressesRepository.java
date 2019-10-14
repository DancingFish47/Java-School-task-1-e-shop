package com.rychkov.eshop.repositorys;

import com.rychkov.eshop.entitys.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressesRepository extends CrudRepository<Address, Integer> {
}
