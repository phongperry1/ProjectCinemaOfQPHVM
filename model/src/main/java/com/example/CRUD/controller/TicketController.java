package com.example.CRUD.controller;

import com.example.CRUD.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/revenue")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public String viewRevenueByMovie(Model model) {
        Map<String, Double> revenueByMovie = ticketService.calculateRevenueByMovie();
        String revenueByMovieJson = new Gson().toJson(revenueByMovie);
        model.addAttribute("revenueByMovie", revenueByMovieJson);
        return "revenue";
    }

    @GetMapping("/day")
    public String viewRevenueByDay(Model model) {
        Map<String, Map<LocalDate, Double>> revenueByDay = ticketService.calculateRevenueByDay();
        String revenueByDayJson = new Gson().toJson(revenueByDay);
        model.addAttribute("revenueData", revenueByDayJson);
        model.addAttribute("revenueType", "Revenue By Day");
        return "revenueByDay";
    }

    @GetMapping("/month")
    public String viewRevenueByMonth(Model model) {
        Map<String, Map<String, Double>> revenueByMonth = ticketService.calculateRevenueByMonth();
        String revenueByMonthJson = new Gson().toJson(revenueByMonth);
        model.addAttribute("revenueData", revenueByMonthJson);
        model.addAttribute("revenueType", "Revenue By Month");
        return "revenueByMonth";
    }
}
