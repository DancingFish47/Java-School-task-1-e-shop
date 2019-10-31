package com.rychkov.eshop.controllers;

import com.rychkov.eshop.exceptions.*;
import net.minidev.json.JSONObject;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@ControllerAdvice
class ExceptionHandlerController {
    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(FailedToDeleteAddressException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo handleException(FailedToDeleteAddressException e) {
        return new ErrorInfo(e.getMessage());
    }

    @ExceptionHandler(OutOfStockException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo handleException(OutOfStockException e) {
        return new ErrorInfo(e.getMessage());
    }

    @ExceptionHandler(BookException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo handleException(BookException e) {
        return new ErrorInfo(e.getMessage());
    }

    @ExceptionHandler(FailedToChangeStatusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo handleException(FailedToChangeStatusException e) {
        return new ErrorInfo(e.getMessage());
    }

    @ExceptionHandler(FailedToRepeatOrderException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo handleException(FailedToRepeatOrderException e) {
        return new ErrorInfo(e.getMessage());
    }

    @ExceptionHandler(GenreException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo handleException(GenreException e) {
        return new ErrorInfo(e.getMessage());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo handleException(PasswordMismatchException e) {
        return new ErrorInfo(e.getMessage());
    }

    @ExceptionHandler(ProcessOrderException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo handleException(ProcessOrderException e) {
        return new ErrorInfo(e.getMessage());
    }

    @ExceptionHandler(ReturnBooksToStockException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo handleException(ReturnBooksToStockException e) {
        return new ErrorInfo(e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ModelAndView handleException(NoHandlerFoundException e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView
    defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation
                (e.getClass(), ResponseStatus.class) != null)
            throw e;

        // Otherwise setup and send the user to a default error-view.
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }



    static class ErrorInfo{
        public boolean error;
        public String message;

        ErrorInfo(String message){
            this.message = message;
            this.error = true;
        }
    }
}
