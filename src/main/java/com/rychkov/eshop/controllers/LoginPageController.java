package com.rychkov.eshop.controllers;

import com.rychkov.eshop.entitys.User;
import com.rychkov.eshop.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("currentUser")
@RequestMapping("/login")
public class LoginPageController {


    @GetMapping
    public String loginForm() {
        return "login";
    }



}

