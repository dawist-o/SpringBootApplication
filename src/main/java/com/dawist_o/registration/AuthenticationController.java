package com.dawist_o.registration;

import com.dawist_o.service.userService.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final RegistrationService registrationService;
    private final AppUserService appUserService;

    @PostMapping("/register")
    public String register(@RequestBody RegistrationRequest request) throws Exception {
        String token = registrationService.register(request);
        return "redirect:login";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        System.out.println("register");
        return "register";
    }

    @GetMapping("/login")
    public String login(Model model) {
        System.out.println("login");
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String loginPost(@RequestBody RegistrationRequest request) throws Exception {
        System.out.println("Post");
        if (request == null)
            throw new Exception("ew");
        System.out.println(request);
        return request.toString();
    }

}
