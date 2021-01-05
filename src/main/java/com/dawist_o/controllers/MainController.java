package com.dawist_o.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/homepage")
    public String home(Model model) {
        model.addAttribute("title", "Homepage");
        return "homepage";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About me");
        return "about";
    }
}

