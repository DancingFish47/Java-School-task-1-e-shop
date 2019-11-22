package com.rychkov.eshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

    @GetMapping({"/", "/homepage"})
    public String homepage() {
        return "redirect:/books";
    }
    @GetMapping("/null")
    public void nullTest(){
        throw new NullPointerException();
    }
}
/*
  TODO
     1) Ddd autovalidate
                                               2) Docker/Docker Compose
                     3) Flyway for sql*
                                               4) Logger
             5) backend validation
             6) Junit tests
             7) Technical solution doc
                                               8) java doc?
                         9) jenkinks, teamcity, ci*
                         10) LIGHT THEME FOR REVIEW
                         11)Front validation edit rules


*/
