package com.example.CRUD.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.example.mo.Movie;
import com.example.mo.Showtime;

@Controller
@RequestMapping("/showtime")
public class ShowtimeController {

    @Autowired
    private ShowtimeService service;

    @GetMapping
    public String showTheaterList(@RequestParam(name = "movieID", required = false) Integer movieID, Model model) {
        List<Showtime> listShowtime;
        if (movieID != null) {
            listShowtime = service.getShowtimesByMovieID(movieID);
        } else {
            listShowtime = service.listAll();
        }
        model.addAttribute("listShowtime", listShowtime);
        model.addAttribute("movieID", movieID);
        return "showtime";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("showtime", new Showtime());
        model.addAttribute("pageTitle", "Add New Showtime");
        return "showtime_form";
    }

    @PostMapping("/save")
    public String saveShowtime(@ModelAttribute Showtime showtime, RedirectAttributes ra, BindingResult result) {
        if (result.hasErrors()) {
            return "showtime_form";
        }

        try {
            service.save(showtime);
            // Add success message and redirect
            ra.addFlashAttribute("message", "The showtime has been saved successfully.");
            return "redirect:/showtime";
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

    @GetMapping("/book/{movieID}")
    public String showMovieDetail(@PathVariable("movieID") Integer movieID, Model model) {
        List<Showtime> listShowtime = service.getShowtimesByMovieID(movieID);
        model.addAttribute("listShowtime", listShowtime);
        model.addAttribute("movieID", movieID);
        return "book";
    }

        @GetMapping("/api/showtime/getTheaterId")
    public ResponseEntity<Integer> getTheaterIdByScreeningRoomId(@RequestParam Integer screeningRoomId) {
        Integer theaterId = service.getTheaterIdByScreeningRoomId(screeningRoomId);
        if (theaterId != null) {
            return ResponseEntity.ok(theaterId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}