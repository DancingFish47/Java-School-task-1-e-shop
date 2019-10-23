package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.UserDto;
import com.rychkov.eshop.entitys.Address;
import com.rychkov.eshop.entitys.User;
import com.rychkov.eshop.exceptions.EmailExistsException;
import com.rychkov.eshop.exceptions.PasswordMismatchException;
import com.rychkov.eshop.exceptions.UsernameExistsException;
import net.minidev.json.JSONObject;


public interface UserService {
    User registerNewUser(UserDto userDto) throws EmailExistsException, UsernameExistsException;

    User changeMainSettings(JSONObject edit, Integer userId);

    void changePassword(JSONObject edit, Integer userId) throws PasswordMismatchException;

    Address editAddress(JSONObject edit, Integer id);

    boolean deleteAddressById(Integer addressId);

    Address saveNewAddress(JSONObject newAddress, User user);
}
