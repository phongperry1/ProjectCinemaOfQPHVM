package com.example.CRUD.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.CRUD.service.PromotionsService;
import com.example.mo.Promotions;

@Controller
public class PromotionsController {
    @Autowired private PromotionsService service;


     @GetMapping("/promotions")
    public String showNewsList(Model model) {
        List<Promotions> listPromotions = service.listAll();
        model.addAttribute("listPromotions", listPromotions);
        return "promotions"; 
    }

    @GetMapping("/promotions/new")
    public String showNewForm(Model model) {
        model.addAttribute("promotions", new Promotions());
        model.addAttribute("pageTitle", "Add New Promotions");
        return "promotions_form";
    }

     @PostMapping("/promotions/save")
    public String savePromotions(Promotions promotions, RedirectAttributes ra) {
        
        service.save(promotions);
        ra.addFlashAttribute("message", "The promotions has been saved successfully.");
        return "redirect:/promotions";
        
    }

    @GetMapping("promotions/edit/{PromotionID}")
    public String showEditForm(@PathVariable("PromotionID") Integer PromotionID, Model model, RedirectAttributes ra) {
        try {
            Promotions promotions = service.get(PromotionID);
            model.addAttribute("promotions", promotions);
            model.addAttribute("pageTitle", "Edit User (ID: " + PromotionID + ")");
            return "promotions_form";
        } catch (PromotionsNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
             return "redirect:/promotions";
        }
        
        
    }


    @GetMapping("promotions/delete/{PromotionID}")
    public String deletePromotions(@PathVariable("PromotionID") Integer PromotionID, RedirectAttributes ra) {
        try {
            service.delete(PromotionID);
            
        } catch (PromotionsNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/promotions";
        
        
    }





}