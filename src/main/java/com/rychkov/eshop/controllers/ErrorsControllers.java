package com.rychkov.eshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorsControllers {
    @GetMapping("/error/403")
    public String error403(){
        return "403";
    }
}
