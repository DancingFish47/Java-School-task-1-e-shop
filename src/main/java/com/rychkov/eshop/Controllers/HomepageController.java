package com.rychkov.eshop.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для домашней страницы.
 */
@Controller
public class HomepageController {
    @GetMapping("/")
    public String homepage(){
        return "homepage";
    }
}
//DDL auto validate
