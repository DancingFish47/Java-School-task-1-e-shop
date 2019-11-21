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
                    4) Custom error pages
                                               5) Docker/Docker Compose
                     7) Flyway for sql*
                                               9) Logger
             10) backend validation
                                               12) Junit tests
                                               13) Technical solution doc
                                               14) java doc?
                         15) jenkinks, teamcity, ci*
         16) JMeter
        17) mockito?
        18) Selenium + CI*
                         19) LIGHT THEME FOR REVIEW
                         20)Front validation edit rules


*/
