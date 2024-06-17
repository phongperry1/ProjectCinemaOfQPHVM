package com.example.CRUD.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.CRUD.service.TheaterService;
import com.example.mo.Theater;

@Controller
public class TheaterController {

    @Autowired
    private TheaterService service;

    @GetMapping("/theater")
    public String showTheaterList(Model model) {
        List<Theater> listTheater = service.listAll();
        model.addAttribute("listTheater", listTheater);
        return "theater";
    }

    @GetMapping("/theater/new")
    public String showNewForm(Model model) {
        model.addAttribute("theater", new Theater());
        model.addAttribute("pageTitle", "Add New User");
        return "theater_form";
    }

    @PostMapping("/theater/save")
    public String saveTheater(Theater theater, RedirectAttributes ra) {

        service.save(theater);
        ra.addFlashAttribute("message", "The theater has been saved successfully.");
        return "redirect:/theater";

    }

    @GetMapping("theater/edit/{TheaterID}")
    public String showEditForm(@PathVariable("TheaterID") Integer TheaterID, Model model, RedirectAttributes ra) {
        try {
            Theater theater = service.get(TheaterID);
            model.addAttribute("theater", theater);
            model.addAttribute("pageTitle", "Edit User (ID: " + TheaterID + ")");
            return "theater_form";
        } catch (TheaterNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/theater";
        }

    }

    @GetMapping("theater/delete/{TheaterID}")
    public String deleteTheater(@PathVariable("TheaterID") Integer TheaterID, RedirectAttributes ra) {
        try {
            service.delete(TheaterID);

        } catch (TheaterNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/theater";

    }

}