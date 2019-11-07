package com.rychkov.eshop.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
public class ExceptionsPageController {
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model){
        model.addAttribute("accessdenied", "access denied!");
        return "error";
    }

    @RequestMapping(value = "/exceptions", method = RequestMethod.GET)
    public String someException(){
        throw new NullPointerException();
    }
    @RequestMapping(value = "/sqlexception", method = RequestMethod.GET)
    public String sqlException(){
        throw new DataIntegrityViolationException("");
    }

}
