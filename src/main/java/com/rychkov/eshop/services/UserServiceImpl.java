package com.rychkov.eshop.services;

import com.rychkov.eshop.dtos.UserDto;
import com.rychkov.eshop.entitys.User;
import com.rychkov.eshop.exceptions.EmailExistsException;
import com.rychkov.eshop.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Transactional
    @Override
    public User registerNewUser(UserDto userDto) throws EmailExistsException {
        if(emailExists(userDto.getEmail())) throw new EmailExistsException("This email is already registered");
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setBirthdate(userDto.getBirthdate());
        user.setEmail(userDto.getEmail().toLowerCase());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserRole("User");
        return userRepository.save(user);
    }

    private boolean emailExists(String email) {
        User user = userRepository.findByEmail(email.toLowerCase());
        if (user != null) {
            return true;
        }
        return false;
    }
}
