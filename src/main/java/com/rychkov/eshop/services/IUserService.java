package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.UserDto;
import com.rychkov.eshop.entitys.User;
import com.rychkov.eshop.exceptions.EmailExistsException;
import org.springframework.stereotype.Service;


public interface IUserService {
    User registerNewUser(UserDto userDto) throws EmailExistsException;
}
