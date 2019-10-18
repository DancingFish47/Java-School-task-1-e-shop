package com.rychkov.eshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;


@Controller
public class HomepageController {
    @GetMapping({"/", "/homepage"})
    public String homepage(Model model, Principal principal){
        return "homepage";
    }
}
//TODO DDL auto validate
