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

import com.example.CRUD.service.FoodService;
import com.example.mo.Food;






@Controller
public class FoodController {
    @Autowired private FoodService service;

      @GetMapping("/food")
    public String showNewsList(Model model) {
        List<Food> listFood = service.listAll();
        model.addAttribute("listFood", listFood);
        return "food"; 
    }

    @GetMapping("/food/new")
    public String showNewForm(Model model) {
        model.addAttribute("food", new Food());
        model.addAttribute("pageTitle", "Add New Food");
        return "food_form";
    }

     @PostMapping("/food/save")
    public String saveFood(@ModelAttribute("food") Food food, @RequestParam("image") MultipartFile multipartFile, RedirectAttributes ra) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        food.setPhotoFood(fileName);
        Food savedFood = service.save(food);
        String uploadDir = "food-photo/" + savedFood.getFoodID();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        ra.addFlashAttribute("message", "The food has been saved successfully.");
        return "redirect:/food";
        
    }


    @GetMapping("food/edit/{FoodID}")
    public String showEditForm(@PathVariable("FoodID") Integer FoodID, Model model, RedirectAttributes ra) {
        try {
            Food food = service.get(FoodID);
            model.addAttribute("food", food);
            model.addAttribute("pageTitle", "Edit Food (ID: " + FoodID + ")");
            return "food_form";
        } catch (FoodNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
             return "redirect:/food";
        }
        
        
    }


    @GetMapping("food/delete/{FoodID}")
    public String deleteFood(@PathVariable("FoodID") Integer FoodID, RedirectAttributes ra) {
        try {
            service.delete(FoodID);
            
        } catch (FoodNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/food";
        
        
    }
}
