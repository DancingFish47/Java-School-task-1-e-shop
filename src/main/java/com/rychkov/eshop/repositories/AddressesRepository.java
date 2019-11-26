package com.rychkov.eshop.repositories;

import com.rychkov.eshop.entities.Address;
import com.rychkov.eshop.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressesRepository extends CrudRepository<Address, Integer> {
    List<Address> findAllOrderByUser(User user);

    List<Address> findAllByAddressStatus_NameAndUser(String addressStatusName, User user);
}
