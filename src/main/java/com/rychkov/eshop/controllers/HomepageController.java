package com.rychkov.eshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class HomepageController {
    @GetMapping({"/", "/homepage"})
    public String homepage(Model model, Principal principal){
        if(principal!=null) model.addAttribute(principal.getName(), "username");
        else model.addAttribute("username", "anonymous");
        return "homepage";
    }
}
//TODO DDL auto validate
