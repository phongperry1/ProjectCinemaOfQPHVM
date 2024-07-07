package com.example.CRUD.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.CRUD.service.MovieService;
import com.example.CRUD.service.NewsService;
import com.example.CRUD.service.PromotionsService;
import com.example.CRUD.service.TheaterService;
import com.example.mo.Movie;
import com.example.mo.News;
import com.example.mo.Promotions;
import com.example.mo.Theater;

@Controller
@RequestMapping("/guest")
public class GuestController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private PromotionsService promotionService;

    @Autowired
    private TheaterService theaterService;

    @GetMapping("/")
    public String showHome(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "home_Guest"; // This should match your Thymeleaf template name
    }

    @GetMapping("/newlist_guest")
    public String showNew(Model model) {
        List<News> listNews = newsService.getAllNews();
        List<Movie> movies = movieService.getAllMovies();
        List<Promotions> promotions = promotionService.listAll();
        model.addAttribute("promotions", promotions);
        model.addAttribute("listNews", listNews);
        model.addAttribute("movies", movies);
        return "newlist_guest";
    }

    @GetMapping("/theaters_Guest")
    public String getTheaters(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "location", required = false) String location,
            Model model) {

        List<Theater> theaters;
        if (keyword != null && !keyword.isEmpty() && location != null && !location.isEmpty()) {
            theaters = theaterService.searchTheaters(keyword);
        } else if (keyword != null && !keyword.isEmpty()) {
            theaters = theaterService.searchTheaters(keyword);
            model.addAttribute("keyword", keyword);
        } else if (location != null && !location.isEmpty()) {
            theaters = theaterService.searchTheatersByLocation(location);
        } else {
            theaters = theaterService.getAllTheaters();
        }

        model.addAttribute("theaters", theaters);
        List<Promotions> promotions = promotionService.listAll();
        model.addAttribute("promotions", promotions);
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);

        return "theaters_Guest"; // Thymeleaf template name
    }

    @GetMapping("/promotionDetailGuest/{id}")
    public String showPromotionDetailGuest(@PathVariable("id") Integer id, Model model) {
        Promotions promotion = promotionService.getPromotionById(id);
        if (promotion != null) {
            model.addAttribute("promotion", promotion);
            return "promotion_detail_guest";
        } else {
            return "redirect:/theaters_Guest";
        }
    }

    @GetMapping("/book_Guest/{id}")
    public String showMovieDetail(@PathVariable("id") Integer id, Model model, Principal principal) {
        Movie movie = movieService.getMovieById(id);
            model.addAttribute("movie", movie);
            return "book_Guest"; // Tên của trang HTML cho chi tiết phim
    }
}
