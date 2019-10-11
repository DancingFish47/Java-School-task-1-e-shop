package com.rychkov.eshop.Controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;

public class ErrorPageController implements ErrorController {
    private static final String PATH = "/error";

    @GetMapping(value=PATH)
    public String error() {
        return "Error haven";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

}
