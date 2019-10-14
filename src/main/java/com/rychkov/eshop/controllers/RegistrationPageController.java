package com.rychkov.eshop.controllers;

import com.rychkov.eshop.repositorys.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationPageController {
    @GetMapping
    public String registrationpage() {
        return "registration";
    }

}


