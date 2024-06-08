package com.example.Minh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainController {
    
    @GetMapping("/home")
    public String showHomePage() {
        System.out.println("main controller");
        return "home";
    }
}
