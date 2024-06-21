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

import com.example.CRUD.service.TheaterService;
import com.example.mo.Theater;
import com.example.CRUD.config.FileUploadUtil;
import com.example.CRUD.controller.TheaterNotFoundException;

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
        model.addAttribute("pageTitle", "Add New Theater");
        return "theater_form";
    }

    @PostMapping("/theater/save")
    public String saveTheater(@ModelAttribute("theater") Theater theater,
                              @RequestParam("image") MultipartFile multipartFile,
                              RedirectAttributes ra) {
        try {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            if (!fileName.isEmpty()) {
                theater.setPhotoTheater(fileName);
            }

            Theater savedTheater = service.save(theater);

            if (!fileName.isEmpty()) {
                String uploadDir = "theater-photo/" + savedTheater.getTheaterID();
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            }

            ra.addFlashAttribute("message", "The theater has been saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            ra.addFlashAttribute("error", "Failed to save the theater due to an error: " + e.getMessage());
        }
        return "redirect:/theater";
    }

    @GetMapping("/theater/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer TheaterID, Model model) throws TheaterNotFoundException {
        Theater theater = service.get(TheaterID);
        model.addAttribute("theater", theater);
        model.addAttribute("pageTitle", "Edit Theater (ID: " + TheaterID + ")");
        return "theater_form";
    }

    @GetMapping("/theater/delete/{id}")
    public String deleteTheater(@PathVariable("id") Integer TheaterID, RedirectAttributes ra) throws TheaterNotFoundException {
        service.delete(TheaterID);
        ra.addFlashAttribute("message", "The theater ID " + TheaterID + " has been deleted.");
        return "redirect:/theater";
    }

    @GetMapping("/all-theaters")
    public String showAllTheaters(Model model) {
        List<Theater> allTheaters = service.listAll();
        model.addAttribute("theaters", allTheaters);
        return "all_theaters";
    }
}
