package com.dawist_o.controllers;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.thymeleaf.TemplateEngine;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class CustomErrorController implements ErrorController {

    private final TemplateEngine templateEngine;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errValue", statusCode);
                model.addAttribute("message", "The page you are looking for was not found.");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errValue", statusCode);
                model.addAttribute("message", "Internal server error");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "errors/error-forbidden";
            } else {
                model.addAttribute("errValue", statusCode);
                model.addAttribute("message", "Error");
            }
        }
        return "errors/error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
