package com.rychkov.eshop.controllers;


import com.rychkov.eshop.dtos.AddressDto;
import com.rychkov.eshop.dtos.ResponseDto;
import com.rychkov.eshop.dtos.UserMainInfoDto;
import com.rychkov.eshop.dtos.PasswordDto;
import com.rychkov.eshop.entities.Address;
import com.rychkov.eshop.entities.User;
import com.rychkov.eshop.exceptions.FailedToDeleteAddressException;
import com.rychkov.eshop.exceptions.JsonException;
import com.rychkov.eshop.exceptions.PasswordMismatchException;
import com.rychkov.eshop.repositories.AddressesRepository;
import com.rychkov.eshop.repositories.UserRepository;
import com.rychkov.eshop.services.UserService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

import static com.rychkov.eshop.configurations.AppConfiguration.ADDRESS_EXISTS;

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
        model.addAttribute("addresses", addressesRepository.findAllByAddressStatus_NameAndUser(ADDRESS_EXISTS, userRepository.findByUsername(principal.getName())));
        return "profileSettings";
    }

    @RequestMapping(value = "/cancelMainEdit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto profileEditMainSettings(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        return ResponseDto.builder()
                .error(false)
                .user(user)
                .build();
    }

    @RequestMapping(value = "/saveMainEdit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto profileSaveMainSettings(@RequestBody UserMainInfoDto userMainInfoDto, Principal principal) throws JsonException {
        userService.editMainInfo(userMainInfoDto, userRepository.findByUsername(principal.getName()).getId());
        return ResponseDto.builder()
                .userMainInfoDto(userMainInfoDto)
                .error(false)
                .build();
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto profileChangePassword(@RequestBody PasswordDto edit, Principal principal) throws PasswordMismatchException {
        userService.changePassword(edit, userRepository.findByUsername(principal.getName()).getId());
        return ResponseDto.builder()
                .message("Password has been changed!")
                .build();
    }

    @RequestMapping(value = "/getAddressById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getAddressById(@RequestBody Integer addressId) {
        ResponseDto responseDto;
        Optional<Address> address = addressesRepository.findById(addressId);
        if (address.isPresent()) {
            responseDto = ResponseDto.builder()
                    .address(address.get())
                    .build();
        } else {
            responseDto = ResponseDto.builder()
                    .error(true)
                    .message("Could not retrieve address from database")
                    .build();
        }
        return responseDto;
    }

    @RequestMapping(value = "/saveEditAddress", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto saveEditAddress(@RequestBody AddressDto edit) {
        ResponseDto responseDto;
        Address address = userService.editAddress(edit);
        if (address != null) {
            responseDto = ResponseDto.builder()
                    .error(false)
                    .message("Address had been edited!")
                    .address(address)
                    .build();
        } else {
            responseDto = ResponseDto.builder()
                    .error(true)
                    .message("Failed to edit address!")
                    .build();
        }
        return responseDto;
    }

    @RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto deleteAddress(@RequestBody Integer deleteId) throws FailedToDeleteAddressException {
        userService.deleteAddressById(deleteId);
        return ResponseDto.builder()
                .message("Address has been deleted!")
                .build();
    }

    @RequestMapping(value = "/saveNewAddress", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto saveNewAddress(@RequestBody AddressDto newAddress, Principal principal) {
        ResponseDto responseDto;
        User user = userRepository.findByUsername(principal.getName());
        Address address = userService.saveNewAddress(newAddress, user);
        if (address != null) {
            responseDto = ResponseDto.builder()
                    .error(false)
                    .message("New address has been added!")
                    .address(address)
                    .build();
        } else {
            responseDto = ResponseDto.builder()
                    .error(true)
                    .message("Failed to add new address!")
                    .build();
        }
        return responseDto;
    }

}
