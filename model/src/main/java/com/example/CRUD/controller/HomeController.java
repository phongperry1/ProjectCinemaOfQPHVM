package com.example.CRUD.controller;

import com.example.mo.Movie;
import com.example.mo.Promotions;
import com.example.mo.Users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.CRUD.service.MovieService;
import com.example.CRUD.service.PromotionsService;
import com.example.Repository.UserRepository;
import com.example.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final MovieService movieService;
    @Autowired
    private PromotionsService service;

    @Autowired
    private UserService userService;

    public HomeController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        Users user = userService.getUsersById(1);

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
        model.addAttribute("user", user);
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

    // @GetMapping("/pro")
    // public String showNewsList(Model model) {
    // List<Promotions> listPromotions = service.listAll();
    // model.addAttribute("listPromotions", listPromotions);
    // return "test";
    // }

    @Autowired
    private UserRepository userRepo;

    @ModelAttribute
    public void commonUser(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            Users user = userRepo.findByEmail(email);
            m.addAttribute("user", user);
        }

    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/signin")
    public String login() {
        return "login";
    }

    /*
     * @GetMapping("/user/profile") public String profile(Principal p, Model m) {
     * String email = p.getName(); User user = userRepo.findByEmail(email);
     * m.addAttribute("user", user); return "profile"; }
     * 
     * @GetMapping("/user/home") public String home() { return "home"; }
     */

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute Users user, HttpSession session, Model m, HttpServletRequest request) {

        // System.out.println(user);
        String url = request.getRequestURL().toString();

        // System.out.println(url); http://localhost:8080/saveUser
        url = url.replace(request.getServletPath(), "");
        // System.out.println(url);
        // //http://localhost:8080/verify?code=3453sdfsdcsadcscd

        Users u = userService.saveUser(user, url);

        if (u != null) { // System.out.println("save sucess");
            session.setAttribute("msg", "Register successfully");

        } else { // System.out.println("error in server");
            session.setAttribute("msg", "Something wrong server");
        }

        return "redirect:/register";
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code, Model m) {
        boolean f = userService.verifyAccount(code);

        if (f) {
            m.addAttribute("msg", "Sucessfully your account is verified");
        } else {
            m.addAttribute("msg", "may be your vefication code is incorrect or already veified ");
        }

        return "message";
    }
}
