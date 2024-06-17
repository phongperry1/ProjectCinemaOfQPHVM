package com.example.CRUD.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.CRUD.service.ShowtimeService;
import com.example.CRUD.service.TheaterService;
import com.example.mo.Showtime;
import com.example.mo.Theater;



@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private ShowtimeService showtimeSer;
    @Autowired
    private TheaterService theaterSer;

    @GetMapping
    public String showBookingPage() {
        return "booking";
    }

    @GetMapping("/showtimes/{date}")
    @ResponseBody
    public List<Showtime> getShowtimesByDate(@PathVariable String date, Model model) {
        Date showDate = Date.valueOf(date);
        List<Showtime> showtimes = showtimeSer.getShowtimesByDate(showDate);
        return showtimes;
    }

        @GetMapping("/booking")
    public String booking(Model model) {
        List<Theater> theaters = theaterSer.listAll(); 
        model.addAttribute("theaters", theaters); 
        return "booking"; 
    }
}
