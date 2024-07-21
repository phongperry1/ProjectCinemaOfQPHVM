package com.example.CRUD.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.example.CRUD.config.FileUploadUtil;
import com.example.CRUD.service.MovieService;
import com.example.CRUD.service.TheaterService;
import com.example.mo.Movie;
import com.example.mo.Theater;

@Controller
public class TheaterController {

    @Autowired
    private TheaterService service;
    @Autowired
    private MovieService movieService;

    @GetMapping("/theater")
    public String viewTheaterPage(Model model) {
        List<Theater> listTheaters = service.listAll();
        model.addAttribute("listTheaters", listTheaters);
        return "theater";
    }

    @GetMapping("/theater/new")
    public String showNewForm(Model model) {
        model.addAttribute("theater", new Theater());
        model.addAttribute("pageTitle", "Add New Theater");
        return "theater_form";
    }

    @PostMapping("/theater/save")
public String saveTheater(
        @ModelAttribute("theater") Theater theater,
        @RequestParam(value = "photoTheaterFile", required = false) MultipartFile multipartFile,
        @RequestParam("movieID") String movieIDs, // Add this parameter to capture the movie IDs
        RedirectAttributes ra, Principal principal) {

    try {
        // Set cinemaOwnerID from Principal
        theater.setCinemaOwnerID(getCinemaOwnerIDFromPrincipal(principal));

        // Parse movie IDs and associate with the theater
        Set<Movie> movies = new HashSet<>();
        for (String movieID : movieIDs.split(";")) {
            Movie movie = movieService.getMovieById(Integer.parseInt(movieID.trim()));
            if (movie != null) {
                movies.add(movie);
            }
        }
        theater.setMovies(movies);

        // Handle file upload
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            theater.setPhotoTheater(fileName);
            Theater savedTheater = service.save(theater);
            String uploadDir = "theater-photo/" + savedTheater.getTheaterID();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            service.save(theater); // Save theater if no file is uploaded
        }

        ra.addFlashAttribute("message", "The theater has been saved successfully.");
    } catch (IOException e) {
        e.printStackTrace();
        ra.addFlashAttribute("error", "Failed to save the theater due to an error: " + e.getMessage());
    }
    return "redirect:/theater";
}

    @GetMapping("/theater/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Theater theater = service.get(id);
            model.addAttribute("theater", theater);
            model.addAttribute("pageTitle", "Edit Theater (ID: " + id + ")");
            return "theater_form";
        } catch (TheaterNotFoundException e) {
            ra.addFlashAttribute("error", "Theater not found");
            return "redirect:/theater";
        }
    }

    @GetMapping("/theater/delete/{id}")
    public String deleteTheater(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The theater has been deleted successfully.");
        } catch (TheaterNotFoundException e) {
            ra.addFlashAttribute("error", "Failed to delete the theater.");
        }
        return "redirect:/theater";
    }

    private Integer getCinemaOwnerIDFromPrincipal(Principal principal) {
        // Implement method to get cinemaOwnerID from Principal
        return 1; // Temporary value for demonstration
    }
}