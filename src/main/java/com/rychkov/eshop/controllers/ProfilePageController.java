package com.rychkov.eshop.controllers;

import com.rychkov.eshop.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/profileSettings")
public class ProfilePageController {
    @Autowired
    UserRepository userRepository;
    @GetMapping
    public String profileSettings(Model model, Principal principal){
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        return "profileSettings";
    }
}
