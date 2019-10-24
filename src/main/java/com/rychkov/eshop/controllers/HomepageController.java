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
  2) Exception handling through controller advice
   3) 2 more functions for admin page
    4) Custom error pages
     5) Figure out "Docker"
      6) Sonar?
       7)Wildfly or Flyway something like that for sql
*/

