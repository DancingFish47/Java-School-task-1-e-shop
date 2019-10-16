package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.UserDto;
import com.rychkov.eshop.entitys.User;
import com.rychkov.eshop.exceptions.EmailExistsException;



public interface UserService {
    User registerNewUser(UserDto userDto) throws EmailExistsException;
}
