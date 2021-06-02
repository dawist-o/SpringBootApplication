package com.dawist_o.authentication.controllers;

import com.dawist_o.authentication.exceptions.InvalidRegistrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RegistrationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidRegistrationException.class)
    protected String handleInvalidRegistrationException(InvalidRegistrationException exception, Model model) {
        log.error("Exception in handleInvalidRegistrationException : " + exception);
        model.addAttribute("errMsg", exception.getMessage());
        return "register";
    }
}
