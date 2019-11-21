package com.rychkov.eshop.controllers;


import com.rychkov.eshop.entitys.Address;
import com.rychkov.eshop.entitys.User;
import com.rychkov.eshop.exceptions.FailedToDeleteAddressException;
import com.rychkov.eshop.exceptions.PasswordMismatchException;
import com.rychkov.eshop.repositorys.AddressesRepository;
import com.rychkov.eshop.repositorys.UserRepository;
import com.rychkov.eshop.services.UserService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profileSettings")
public class ProfilePageController {
    private final AddressesRepository addressesRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    public String profileSettings(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("addresses", addressesRepository.findAllOrderByUser(userRepository.findByUsername(principal.getName())));
        return "profileSettings";
    }

    @RequestMapping(value = "/cancelMainEdit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject profileEditMainSettings(Principal principal) {
        JSONObject result = new JSONObject();
        User user = userRepository.findByUsername(principal.getName());
        if (user != null) {
            result.put("error", false);
            result.put("user", user);
        } else {
            result.put("error", true);
            result.put("message", "Something happened");
        }
        return result;
    }

    @RequestMapping(value = "/saveMainEdit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject profileSaveMainSettings(@RequestBody JSONObject edit, Principal principal) {
        JSONObject result = new JSONObject();
        User user = userService.changeMainSettings(edit, userRepository.findByUsername(principal.getName()).getId());
        if (user != null) {
            result.put("error", false);
            result.put("user", user);
        } else {
            result.put("error", true);
            result.put("message", "Saving failed");
        }
        return result;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject profileChangePassword(@RequestBody JSONObject edit, Principal principal) throws PasswordMismatchException {
        JSONObject result = new JSONObject();
        userService.changePassword(edit, userRepository.findByUsername(principal.getName()).getId());
        result.put("message", "Password changed!");
        return result;
    }

    @RequestMapping(value = "/getAddressById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getAddressById(@RequestBody JSONObject addressId) {
        JSONObject result = new JSONObject();
        Optional<Address> address = addressesRepository.findById(Integer.valueOf((String) addressId.get("id")));
        if (address.isPresent()) {
            result.put("address", address);
        } else {
            result.put("error", true);
            result.put("message", "Could not retrieve address from database");
        }
        return result;
    }

    @RequestMapping(value = "/saveEditAddress", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveEditAddress(@RequestBody JSONObject edit) {
        JSONObject result = new JSONObject();
        Address address = userService.editAddress(edit, (Integer) edit.get("id"));
        if (address != null) {
            result.put("error", false);
            result.put("message", "Address edited successfully!");
            result.put("address", address);
        } else {
            result.put("error", true);
            result.put("message", "Failed to edit address!");
        }
        return result;
    }

    @RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteAddress(@RequestBody JSONObject deleteId) throws FailedToDeleteAddressException {
        JSONObject result = new JSONObject();
        userService.deleteAddressById(Integer.valueOf((String) deleteId.get("id")));
        result.put("message", "Address deleted!");
        return result;
    }

    @RequestMapping(value = "/saveNewAddress", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveNewAddress(@RequestBody JSONObject newAddress, Principal principal) {
        JSONObject result = new JSONObject();
        User user = userRepository.findByUsername(principal.getName());
        Address address = userService.saveNewAddress(newAddress, user);
        if (address != null) {
            result.put("error", false);
            result.put("message", "New address added!");
            result.put("address", address);
        } else {
            result.put("error", true);
            result.put("message", "Failed to add new address!");
        }
        return result;
    }

}
