package com.example.CRUD.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.CRUD.service.MovieService;
import com.example.CRUD.service.PromotionsService;
import com.example.Repository.UserRepository;
import com.example.Service.UserService;
import com.example.mo.Movie;
import com.example.mo.Promotions;
import com.example.mo.Users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final MovieService movieService;
    private final PromotionsService promotionsService;
    private final UserService userService;
    private final UserRepository userRepo;

    @Autowired
    public HomeController(MovieService movieService, PromotionsService promotionsService, UserService userService, UserRepository userRepo) {
        this.movieService = movieService;
        this.promotionsService = promotionsService;
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new Users()); // Add empty user object to the model for form binding
        return "login";
    }

    @PostMapping("/userLogin")
    public String login(@ModelAttribute("user") Users user, HttpSession session, Model model) {
        Users authenticatedUser = userService.authenticate(user.getEmail(), user.getUserPassword());
        if (authenticatedUser != null) {
            session.setAttribute("user", authenticatedUser);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/home")
    public String showHomePage(Model model, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

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
        List<Promotions> listPromotions = promotionsService.listAll();
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
        return "redirect:/home";
    }

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

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute Users user, HttpSession session, Model model, HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        url = url.replace(request.getServletPath(), "");

        // Ensure member points are not null
        if (user.getMemberPoints() == null) {
            user.setMemberPoints(0);
        }

        Users savedUser = userService.saveUser(user, url);

        if (savedUser != null) {
            session.setAttribute("msg", "Register successfully");
        } else {
            session.setAttribute("msg", "Something went wrong on the server");
        }

        return "redirect:/register";
    }



    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code, Model m) {
        boolean f = userService.verifyAccount(code);

        if (f) {
            m.addAttribute("msg", "Successfully your account is verified");
        } else {
            m.addAttribute("msg", "Your verification code is incorrect or already verified");
        }

        return "message";
    }
}
