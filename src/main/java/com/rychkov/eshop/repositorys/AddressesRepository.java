package com.rychkov.eshop.repositorys;

import com.rychkov.eshop.entitys.Address;
import com.rychkov.eshop.entitys.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressesRepository extends CrudRepository<Address, Integer> {
    List<Address> findAllOrderByUser(User user);

}
