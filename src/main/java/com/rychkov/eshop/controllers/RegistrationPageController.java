package com.rychkov.eshop.controllers;

import com.rychkov.eshop.dtos.UserDto;
import com.rychkov.eshop.entitys.User;
import com.rychkov.eshop.exceptions.EmailExistsException;
import com.rychkov.eshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationPageController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }
    @PostMapping
    public ModelAndView registerNewUser(
    @ModelAttribute("user") @Valid UserDto accountDto,
    BindingResult result,
    Model model){
        User registered = new User();
            if (!result.hasErrors()) {
                registered = createUserAccount(accountDto);
            }
            if (registered == null) {
                result.rejectValue("email", "message.regError");
            }
            if (result.hasErrors()) {
                    model.addAttribute("error", "This email already exists");
                    return new ModelAndView("registration", "user", accountDto);
                }
                else {
                    return new ModelAndView("login", "user", accountDto);
                }
}
    private User createUserAccount(UserDto accountDto) {
        User registered = null;
        try {
            registered = userService.registerNewUser(accountDto);
        } catch (EmailExistsException e) {
            return null;
        }
        return registered;
    }

}


