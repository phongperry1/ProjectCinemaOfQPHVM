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

@Controller
public class TheaterController {

    @Autowired
    private TheaterService service;

    @GetMapping("/theater")
    public String showTheaterList(Model model) {
        List<Theater> listTheater = service.listAll();
        model.addAttribute("listTheater", listTheater);
        return "theater"; // Thay đổi tên view nếu cần thiết
    }

    @GetMapping("/theater/new")
    public String showNewForm(Model model) {
        model.addAttribute("theater", new Theater());
        model.addAttribute("pageTitle", "Add New Theater");
        return "theater_form"; // Thay đổi tên view nếu cần thiết
    }

    @PostMapping("/theater/save")
    public String saveTheater(@ModelAttribute("theater") Theater theater,
                              @RequestParam(value = "image", required = false) MultipartFile multipartFile,
                              RedirectAttributes ra) {
        try {
            // Check if file is not empty before processing
            if (multipartFile != null && !multipartFile.isEmpty()) {
                // Sanitize the file name
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    
                // Set the file name to the theater object
                theater.setPhotoTheater(fileName);
    
                // Save the theater object first to get its ID
                Theater savedTheater = service.save(theater);
    
                // Define the upload directory
                String uploadDir = "theater-photo/" + savedTheater.getTheaterID();
    
                // Save the file to the upload directory
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            } else {
                // Save theater without file
                service.save(theater);
            }
    
            // Add a success message
            ra.addFlashAttribute("message", "The theater has been saved successfully.");
        } catch (IOException e) {
            // Handle any IO exceptions that may occur
            e.printStackTrace();
            ra.addFlashAttribute("error", "Failed to save the theater due to an error: " + e.getMessage());
        }
    
        // Redirect to the theater page
        return "redirect:/theater";
    }
    
    @GetMapping("/theater/edit/{theaterID}")
    public String showEditForm(@PathVariable("theaterID") Integer theaterID, Model model) throws TheaterNotFoundException {
        Theater theater = service.get(theaterID);
        model.addAttribute("theater", theater);
        model.addAttribute("pageTitle", "Edit Theater (ID: " + theaterID + ")");
        return "theater_form"; // Thay đổi tên view nếu cần thiết
    }

    @GetMapping("/theater/delete/{theaterID}")
    public String deleteTheater(@PathVariable("theaterID") Integer theaterID, RedirectAttributes ra) throws TheaterNotFoundException {
        service.delete(theaterID);
        ra.addFlashAttribute("message", "The theater ID " + theaterID + " has been deleted.");
        return "redirect:/theater"; // Thay đổi đường dẫn redirect nếu cần thiết
    }

    @GetMapping("/all-theaters")
    public String showAllTheaters(Model model) {
        List<Theater> allTheaters = service.listAll();
        model.addAttribute("theaters", allTheaters);
        return "all_theaters"; // Thay đổi tên view nếu cần thiết
    }
}
