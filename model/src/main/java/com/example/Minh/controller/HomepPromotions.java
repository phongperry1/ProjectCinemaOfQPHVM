package com.example.Minh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.Minh.model.Promotions;
import com.example.Minh.service.PromotionsService;

public class HomepPromotions {
    @Autowired private PromotionsService service;
    // @GetMapping("/pro")
    // public String showNewsList(Model model) {
    //     List<Promotions> listPromotions = service.listAll();
    //     model.addAttribute("listPromotions", listPromotions);
    //     return "redirect:/view"; 
    // }
    
}
