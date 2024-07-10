package com.example.CRUD.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.CRUD.service.ShowtimeService;
import com.example.CRUD.service.UserService;
import com.example.mo.Showtime;
import com.example.mo.Users;

@Controller
@RequestMapping("/showtime")
public class ShowtimeController {

    @Autowired
    private ShowtimeService service;

    @Autowired
    private UserService userService;

    @GetMapping
    public String showShowtimeList(@RequestParam(name = "movieID", required = false) Integer movieID,
                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                   Model model, Principal principal) {
        Integer cinemaOwnerID = getCinemaOwnerIDFromPrincipal(principal);
        Pageable pageable = PageRequest.of(page, 10); // 10 items per page
        Page<Showtime> showtimePage;
        if (movieID != null) {
            showtimePage = service.getShowtimesByMovieIDAndCinemaOwnerID(movieID, cinemaOwnerID, pageable);
        } else {
            showtimePage = service.listAllByCinemaOwnerID(cinemaOwnerID, pageable);
        }
        model.addAttribute("listShowtime", showtimePage.getContent());
        model.addAttribute("movieID", movieID);
        model.addAttribute("page", showtimePage);
        model.addAttribute("pageTitle", "Manage Showtimes");
        return "showtime";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("showtime", new Showtime());
        model.addAttribute("pageTitle", "Add New Showtime");
        return "showtime_form";
    }

    @PostMapping("/save")
    public String saveShowtime(@ModelAttribute Showtime showtime, RedirectAttributes ra, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "showtime_form";
        }

        try {
            // Set cinemaOwnerID from Principal
            showtime.setCinemaOwnerID(getCinemaOwnerIDFromPrincipal(principal));
            service.save(showtime);
            ra.addFlashAttribute("message", "The showtime has been saved successfully.");
            return "redirect:/showtime?movieID=" + showtime.getMovieID();
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", "Invalid date or time format.");
            return "redirect:/showtime";
        }
    }

    @GetMapping("/edit/{showtimeID}")
    public String showEditForm(@PathVariable("showtimeID") Integer showtimeID, Model model, RedirectAttributes ra) {
        try {
            Showtime showtime = service.get(showtimeID);
            model.addAttribute("showtime", showtime);
            model.addAttribute("pageTitle", "Edit Showtime (ID: " + showtimeID + ")");
            return "showtime_form";
        } catch (ShowtimeNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/showtime";
        }
    }

    @GetMapping("/delete/{showtimeID}")
    public String deleteShowtime(@PathVariable("showtimeID") Integer showtimeID, RedirectAttributes ra) {
        try {
            service.delete(showtimeID);
            ra.addFlashAttribute("message", "The showtime has been deleted successfully.");
            return "redirect:/showtime";
        } catch (ShowtimeNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/showtime";
        }
    }

    private Integer getCinemaOwnerIDFromPrincipal(Principal principal) {
        Users user = userService.getUsersByEmail(principal.getName());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user.getUserId(); // Ensure this returns the correct ID for cinema owner
    }
}
