package com.rychkov.eshop.dtos;

import com.rychkov.eshop.entitys.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;


@Data
public class RegistrationDto {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String birthdate;
    private String email;
    private String role;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username, passwordEncoder.encode(password),
                firstname, lastname, email, birthdate, role);
    }
}
