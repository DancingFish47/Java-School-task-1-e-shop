package com.rychkov.eshop.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginPageController {
    @GetMapping("/login")
        public String signinPage(){
            return "login";
        }
    }

