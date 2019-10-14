package com.rychkov.eshop.controllers;

import com.rychkov.eshop.dtos.RegistrationDto;
import com.rychkov.eshop.repositorys.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationPageController {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationPageController(UserRepository userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registrationpage() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationDto registrationDto){
        userRepo.save(registrationDto.toUser(passwordEncoder));
        return "redirect:/login";
    }



}


