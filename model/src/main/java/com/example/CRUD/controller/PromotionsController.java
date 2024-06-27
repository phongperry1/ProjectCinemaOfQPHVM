package com.example.CRUD.controller;

import java.io.IOException;
import java.security.Principal;
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
import com.example.CRUD.service.UserService;
import com.example.mo.Promotions;
import com.example.mo.Users;

@Controller
public class PromotionsController {

    @Autowired
    private PromotionsService service;

    @Autowired
    private UserService userService;

    @GetMapping("/promotions")
    public String showPromotionsList(Model model, Principal principal) {
        Integer cinemaOwnerID = getCinemaOwnerIDFromPrincipal(principal);
        List<Promotions> listPromotions = service.listAllByCinemaOwnerID(cinemaOwnerID);
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
                                 @RequestParam(value = "image", required = false) MultipartFile multipartFile,
                                 RedirectAttributes ra, Principal principal) {
        try {
            promotions.setCinemaOwnerID(getCinemaOwnerIDFromPrincipal(principal));

            if (multipartFile != null && !multipartFile.isEmpty()) {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                promotions.setPhotoPromotions(fileName);
                Promotions savedPromotions = service.save(promotions);
                String uploadDir = "promotions-photo/" + savedPromotions.getPromotionID();
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            } else {
                service.save(promotions);
            }

            ra.addFlashAttribute("message", "The promotions have been saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            ra.addFlashAttribute("error", "Failed to save the promotions due to an error: " + e.getMessage());
        }

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

    private Integer getCinemaOwnerIDFromPrincipal(Principal principal) {
        Users user = userService.getUsersByEmail(principal.getName());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user.getUserId();
    }
}
