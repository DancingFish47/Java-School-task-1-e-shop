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
    0) Figure out how to stop @Scheduled threads.
  1) Ddd autovalidate
  2) Exception handling through controller advice
    4) Custom error pages
     5) Figure out "Docker"
      6) Sonar?
       7)Wildfly or Flyway something like that for sql
*/

