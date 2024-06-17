package com.example.CRUD.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.CRUD.service.PromotionsService;
import com.example.mo.Promotions;


@Controller
public class PromotionsController {

    @Autowired
    private PromotionsService service;

    @GetMapping("/promotions")
    public String showPromotionsList(Model model) {
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
    public String savePromotions(@ModelAttribute("promotions") Promotions promotions,
                                 @RequestParam("image") MultipartFile multipartFile,
                                 RedirectAttributes ra) {
        try {
            // Sanitize the file name
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            
            // Set the file name to the promotions object
            promotions.setPhotoPromotions(fileName);
            
            // Save the promotions object first to get its ID
            Promotions savedPromotions = service.save(promotions);
            
            // Define the upload directory
            String uploadDir = "promotions-photo/" + savedPromotions.getPromotionID();
            
            // Save the file to the upload directory
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            
            // Add a success message
            ra.addFlashAttribute("message", "The promotions has been saved successfully.");
        } catch (IOException e) {
            // Handle any IO exceptions that may occur
            e.printStackTrace();
            ra.addFlashAttribute("error", "Failed to save the promotions due to an error: " + e.getMessage());
        }
        // Redirect to the promotions page
        return "redirect:/promotions";
    }

    @GetMapping("/promotions/edit/{PromotionID}")
    public String showEditForm(@PathVariable("PromotionID") Integer PromotionID, Model model, RedirectAttributes ra) {
        try {
            Promotions promotions = service.get(PromotionID);
            model.addAttribute("promotions", promotions);
            model.addAttribute("pageTitle", "Edit Promotions (ID: " + PromotionID + ")");
            return "promotions_form";
        } catch (PromotionsNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/promotions";
        }
    }

    @GetMapping("/promotions/delete/{PromotionID}")
    public String deletePromotions(@PathVariable("PromotionID") Integer PromotionID, RedirectAttributes ra) {
        try {
            service.delete(PromotionID);
            ra.addFlashAttribute("message", "The promotion has been deleted.");
        } catch (PromotionsNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/promotions";
    }
}
