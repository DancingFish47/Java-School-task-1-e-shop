package com.rychkov.eshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomepageController {
    @GetMapping({"/", "/homepage"})
    public String homepage(){
        return "homepage";
    }
}
//TODO DDL auto validate
