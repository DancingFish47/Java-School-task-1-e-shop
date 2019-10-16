package com.rychkov.eshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginPageController {
    @GetMapping
    public String loginPage() {
        return "login";
    }

}




