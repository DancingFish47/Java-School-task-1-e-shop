package com.rychkov.eshop.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.rychkov.eshop.entitys.User;
import com.rychkov.eshop.exceptions.PasswordMismatchException;
import com.rychkov.eshop.repositorys.AddressesRepository;
import com.rychkov.eshop.repositorys.UserRepository;
import com.rychkov.eshop.services.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/profileSettings")
public class ProfilePageController {
    @Autowired
    AddressesRepository addressesRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @GetMapping
    public String profileSettings(Model model, Principal principal){
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("addresses", addressesRepository.findAllOrderByUser(userRepository.findByUsername(principal.getName())));
        return "profileSettings";
    }

    @RequestMapping(value = "/cancelMainEdit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject profileEditMainSettings(Principal principal){
        JSONObject result = new JSONObject();
        User user = userRepository.findByUsername(principal.getName());
        if(user != null) {
            result.put("error", false);
            result.put("user", user);
        }
        else{
            result.put("error", true);
            result.put("message", "Something happened");
        }
        return result;
    }

    @RequestMapping(value = "/saveMainEdit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject profileSaveMainSettings(@RequestBody JSONObject edit, Principal principal){
        JSONObject result = new JSONObject();
        User user = userService.changeMainSettings(edit, userRepository.findByUsername(principal.getName()).getId());
        if(user != null) {
            result.put("error", false);
            result.put("user", user);
        }
        else{
            result.put("error", true);
            result.put("message", "Saving failed");
        }
        return result;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject profileChangePassword(@RequestBody JSONObject edit, Principal principal){
        JSONObject result = new JSONObject();
        try{
            userService.changePassword(edit, userRepository.findByUsername(principal.getName()).getId());
            result.put("error", false);
            result.put("message", "Password changed!");
        } catch (PasswordMismatchException e){
            result.put("error", true);
            result.put("message", "Wrong current password!");
        }
        return result;
    }
}
