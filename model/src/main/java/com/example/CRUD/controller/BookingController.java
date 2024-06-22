package com.example.CRUD.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.CRUD.Repository.SeatRepository;
import com.example.CRUD.service.FoodService;
import com.example.CRUD.service.MovieService;
import com.example.CRUD.service.SeatService;
import com.example.CRUD.service.ShowtimeService;
import com.example.CRUD.service.TheaterService;
import com.example.mo.Food;
import com.example.mo.Movie;
import com.example.mo.Seat;
import com.example.mo.ShowtimeDTO;
import com.example.mo.Theater;



@Controller

public class BookingController {

    @Autowired
    private MovieService movieService ;
    @Autowired
    private TheaterService theaterSer;
    @Autowired
    private ShowtimeService showtimeSer;
    @Autowired
    private SeatService seatSer;

    @Autowired
    private FoodService foodSer;


    @GetMapping("/booking/{id}")
    public String showBookingPage(Model model, @PathVariable("id") Integer id) {
        Movie movie = movieService.getMovieById(id);
        List<Theater> theaters = theaterSer.listAll(); 
        List<Food> foods = foodSer.listAll();
        model.addAttribute("theaters", theaters); 
        model.addAttribute("movie", movie);
        model.addAttribute("foods", foods);
        model.addAttribute("movieID", movie.getMovieID());
        return "booking"; 
    }

    @GetMapping("/showtimes/getShowtimes")
    @ResponseBody
    public List<ShowtimeDTO> getShowtimesByMovieIDAndTheaterID(@RequestParam("movieID") Integer movieID, @RequestParam("theaterID") Integer theaterID) {
    List<Object[]> results = showtimeSer.getShowtimesByMovieIDAndTheaterID(movieID, theaterID);
    List<ShowtimeDTO> showtimes = new ArrayList<>();
    for (Object[] result : results) {
        Date showDate = (Date) result[0];
        Time showTime = (Time) result[1];
        showtimes.add(new ShowtimeDTO(showDate, showTime));
    }
    return showtimes;
    }

    // @GetMapping("/api/seats")
    // public List<Seat> getAllSeats() {
    //     return seatSer.getAllSeats();
    // }

    @GetMapping("/foods")
    @ResponseBody
    public List<Food> getAllFoods() {
        return foodSer.listAll();
    }

    // @GetMapping("/{cinemaName}")
    // @ResponseBody
    // public List<String> getShowtimesByCinemaName(@PathVariable String cinemaName) {
    //     Theater theater = theaterSer.findByTheaterName(cinemaName);
    //     if (theater != null) {
    //         return theater.getShowtimes().stream()
    //                 .map(showtime -> showtime.getShowDate() + " " + showtime.getShowTime().toString())
    //                 .collect(Collectors.toList());
    //     }
    //     return Collections.emptyList();
    // }
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
