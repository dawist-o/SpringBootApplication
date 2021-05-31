package com.dawist_o.controllers;

import com.dawist_o.model.user.AppUser;
import com.dawist_o.service.userService.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {

    private AppUserService appUserService;

    @PostMapping("logout")
    public String logout() {
        return "redirect:/homepage";
    }

    @GetMapping
    public String profile(Principal principal, Model model) {
        String userEmail = principal.getName();
        AppUser appUser = appUserService.loadUserByUsername(userEmail);
        model.addAttribute("user", appUser);
        return "profile";
    }
}
