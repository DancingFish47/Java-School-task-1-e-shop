package com.rychkov.eshop.controllers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomepageController {
    @Autowired
    RabbitTemplate template;

    @GetMapping({"/", "/homepage"})
    public String homepage() {
        return "redirect:/books";
    }

    @GetMapping("/emit")
    @ResponseBody
    public String emit() {
        template.setExchange("books_fanout");
        template.convertAndSend("Fanout message");
        return "Emit to exchange-example-3";
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


*/
