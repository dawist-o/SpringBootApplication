package com.dawist_o.authentication.controllers;

import com.dawist_o.authentication.registration.RegistrationRequest;
import com.dawist_o.authentication.registration.RegistrationService;
import com.dawist_o.service.userService.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final RegistrationService registrationService;

    @PostMapping("register")
    public String register(@RequestBody RegistrationRequest request) throws Exception {
        registrationService.register(request);
        return "redirect:login";
    }

    @GetMapping("register")
    public String getRegister(Model model) {
        System.out.println("register");
        return "register";
    }

    @GetMapping("login")
    public String login(Model model) {
        System.out.println("login");
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
