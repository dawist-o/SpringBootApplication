package com.dawist_o.authentication.controllers;

import com.dawist_o.authentication.registration.RegistrationRequest;
import com.dawist_o.authentication.registration.RegistrationService;
import com.dawist_o.service.userService.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final RegistrationService registrationService;

    @PostMapping("register")
    public String registerUrlData(@RequestParam String name, @RequestParam String email,
                                  @RequestParam String pass, @RequestParam String re_pass,
                                  HttpServletRequest httpRequest) {
        RegistrationRequest request = new RegistrationRequest(name, email, pass, re_pass);
        registrationService.register(httpRequest, request);
        return "redirect:/auth/login";
    }

    @GetMapping("register")
    public String getRegister(Model model) {
        return "register";
    }

    @GetMapping("login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("login-error")
    public String loginError(Model model) {
        System.out.println("error");
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("confirm")
    public String confirmToken(@RequestParam String token) {
        registrationService.confirmToken(token);
        return "redirect:/auth/login";
    }

}
