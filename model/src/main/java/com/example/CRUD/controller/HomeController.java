package com.example.CRUD.controller;



import com.example.mo.Movie;
import com.example.mo.Promotions;
import com.example.CRUD.service.MovieService;
import com.example.CRUD.service.PromotionsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final MovieService movieService;
    @Autowired private PromotionsService service;

    public HomeController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        List<Movie> simplifiedMovies = movies.stream()
                .map(m -> {
                    Movie simplifiedMovie = new Movie();
                    simplifiedMovie.setTitle(m.getTitle());
                    simplifiedMovie.setShowTime(m.getShowTime());
                    simplifiedMovie.setAddress(m.getAddress());
                    return simplifiedMovie;
                })
                .collect(Collectors.toList());
        model.addAttribute("movies", simplifiedMovies);
        List<Promotions> listPromotions = service.listAll();
        model.addAttribute("listPromotions", listPromotions);
        return "home";
    }

    @GetMapping("/home/{id}")
    public String getMovieForHome(@PathVariable Integer id, Model model) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            model.addAttribute("movie", movie);
            return "home";
        }
        return "redirect:/";
    }

    //  @GetMapping("/pro")
    // public String showNewsList(Model model) {
    //     List<Promotions> listPromotions = service.listAll();
    //     model.addAttribute("listPromotions", listPromotions);
    //     return "test"; 
    // }
}
