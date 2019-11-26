package com.rychkov.eshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

    @GetMapping({"/", "/homepage"})
    public String homepage() {
        return "redirect:/books";
    }

}
/*
  TODO
     1) Ddd autovalidate
     2) Docker/Docker Compose in root, update wars
     3) Flyway for sql*
     4) sonar coverage
     5) light theme
             4) Logger
             5) backend validation
             7) Technical solution doc
             11)Front validation edit rules


*/
