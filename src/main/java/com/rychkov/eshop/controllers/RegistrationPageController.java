package com.rychkov.eshop.controllers;

import com.rychkov.eshop.dtos.ResponseDto;
import com.rychkov.eshop.dtos.UserDto;
import com.rychkov.eshop.exceptions.EmailExistsException;
import com.rychkov.eshop.exceptions.UsernameExistsException;
import com.rychkov.eshop.services.UserService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Validated
public class RegistrationPageController {
    private final UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm() {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto registerNewUser(
            @Valid @RequestBody UserDto newUser, Errors errors) throws EmailExistsException, UsernameExistsException {
        userService.registerNewUser(newUser);
        return ResponseDto.builder()
                .message("Registration complete!")
                .build();
    }


}


