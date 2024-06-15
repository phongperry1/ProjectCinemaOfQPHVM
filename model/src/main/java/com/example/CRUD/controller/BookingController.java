package com.example.CRUD.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.CRUD.Repository.TheaterRepository;
import com.example.CRUD.service.ShowtimeService;
import com.example.CRUD.service.TheaterService;
import com.example.mo.Showtime;
import com.example.mo.Theater;



@Controller

public class BookingController {

    @Autowired
    private ShowtimeService showtimeSer;
    @Autowired
    private TheaterService theaterSer;


    @GetMapping("/booking")
    public String showBookingPage(Model model) {
        List<Theater> theaters = theaterSer.listAll(); 
        model.addAttribute("theaters", theaters); 
        return "booking"; 
    }

    @GetMapping("/{cinemaName}")
    @ResponseBody
    public List<String> getShowtimesByCinemaName(@PathVariable String cinemaName) {
        Theater theater = theaterSer.findByTheaterName(cinemaName);
        if (theater != null) {
            return theater.getShowtime().stream()
                    .map(showtime -> showtime.getShowDate() + " " + showtime.getShowTime().toString())
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
    // @GetMapping("booking/showtimes/{cinemaName}")
    // @ResponseBody
    // public List<String> getShowtimesByCinemaName(@PathVariable String cinemaName) {
    //     // Viết logic để truy vấn từ cơ sở dữ liệu và trả về danh sách suất chiếu
    //     List<Theater> theater = theaterSer.findByTheaterName(cinemaName); // Ví dụ truy vấn theo tên rạp
    //     // Lấy danh sách suất chiếu từ theater
    //     List<String> showtimes = theater.getShowtimes(); // Phụ thuộc vào cấu trúc dữ liệu của bạn
    //     return showtimes;
    // }
}
