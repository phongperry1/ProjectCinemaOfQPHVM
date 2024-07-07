package com.example.CRUD.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.CRUD.service.MovieService;
import com.example.CRUD.service.NewsService;
import com.example.CRUD.service.PromotionsService;
import com.example.CRUD.service.TheaterService;
import com.example.CRUD.service.TicketService;
import com.example.CRUD.service.UserService;
import com.example.mo.Movie;
import com.example.mo.News;
import com.example.mo.Promotions;
import com.example.mo.Theater;
import com.example.mo.Ticket;
import com.example.mo.Users;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private PromotionsService promotionsService;
    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            Users user = userService.getUsersByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/home")
    public String showHomePage(Model model, Principal principal) {
        Integer cinemaOwnerID = getCinemaOwnerIDFromPrincipal(principal);
        List<Movie> movies = movieService.getAllMovies();
        List<Movie> simplifiedMovies = movies.stream()
                .map(m -> {
                    Movie simplifiedMovie = new Movie();
                    simplifiedMovie.setMovieID(m.getMovieID());
                    simplifiedMovie.setTitle(m.getTitle());
                    simplifiedMovie.setGenre(m.getGenre());
                    simplifiedMovie.setAddress(m.getAddress());
                    return simplifiedMovie;
                })
                .collect(Collectors.toList());
        model.addAttribute("movies", simplifiedMovies);
        List<Promotions> listPromotions = promotionsService.listAllByCinemaOwnerID(cinemaOwnerID);
        model.addAttribute("listPromotions", listPromotions);
        return "home";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Users user = userService.getUsersByEmail(email);
            model.addAttribute("user", user);
        }
        return "profile";
    }

    @PostMapping("/upload-avatar")
    public String changeAvatar(Model model, @RequestParam("file") MultipartFile file, Principal principal)
            throws IOException {
        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);
        String originalFilename = file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(System.getProperty("user.dir") + "/uploads", originalFilename);
        Files.write(fileNameAndPath, file.getBytes());
        user.setProfileImageURL(originalFilename);
        userService.updateUser(user);
        model.addAttribute("user", user);
        return "redirect:/user/profile";
    }

    @GetMapping("/update-profile")
    public String showUpdateProfile(Model model, Principal principal) {
        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);
        model.addAttribute("user", user);
        return "update-profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(Users updatedUser, Principal principal) {
        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);
        if (user != null) {
            user.setUserName(updatedUser.getUserName());
            user.setEmail(updatedUser.getEmail());
            user.setPhone(updatedUser.getPhone());
            user.setLocation(updatedUser.getLocation());
            user.setBirthdate(updatedUser.getBirthdate());
            userService.updateUser(user);
        }
        return "redirect:/user/profile";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model, Principal principal) {
        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);
        model.addAttribute("user", user);
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("password") String password,
                                 Principal principal,
                                 Model model) {
        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);
        userService.updatePassword(user, password);
        model.addAttribute("message", "Password changed successfully.");
        return "change-password";
    }

    @GetMapping("/member-points")
    public String showMemberPoints(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Users user = userService.getUsersByEmail(email);
            if (user != null) {
                model.addAttribute("points", user.getMemberPoints());
            } else {
                model.addAttribute("error", "User not found.");
            }
        }
        return "member-points";
    }

        @GetMapping("/mytickets")
    public String ShowMyTickets(Model model, Principal principal) {
        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);
        int userId = user.getUserId();
        List<Ticket> tickets = ticketService.getTicketsByUserId(userId);
        model.addAttribute("tickets", tickets);
        return "mytickets";
    }
    @Autowired
    private TheaterService service;

    @Autowired
    private PromotionsService promotionService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private NewsService newsService;

    @GetMapping("/theaters")
    public String getTheatersAndPromotions(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "location", required = false) String location,
            Model model) {

        List<Theater> theaters;

        // Search by both keyword and location
        if (keyword != null && !keyword.isEmpty() && location != null && !location.isEmpty()) {
            theaters = service.searchTheaters(keyword);
        }
        // Search by keyword only
        else if (keyword != null && !keyword.isEmpty()) {
            theaters = service.searchTheaters(keyword);
            model.addAttribute("keyword", keyword);
        }
        // Search by location only
        else if (location != null && !location.isEmpty()) {
            theaters = service.searchTheatersByLocation(location);
        }
        // Fetch all theaters if no search criteria provided
        else {
            theaters = service.getAllTheaters();
        }

        model.addAttribute("theaters", theaters);

        // Fetch promotions
        List<Promotions> promotions = promotionService.listAll();
        model.addAttribute("promotions", promotions);

        // Fetch movies (adjust as per your service method)
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);

        return "theaters"; // Thymeleaf template name
    }

    @GetMapping("/promotionDetail/{id}")
    public String showPromotionDetail(@PathVariable("id") Integer id, Model model) {
        Promotions promotion = promotionService.getPromotionById(id);
        if (promotion != null) {
            model.addAttribute("promotion", promotion);
            return "/promotion_detail";
        } else {
            return "redirect:/promotions";
        }
    }

    @GetMapping("/newlist")
    public String showNewList(Model model, Principal principal) {
        // Extract cinema owner ID from principal
        Integer cinemaOwnerID = getCinemaOwnerIDFromPrincipal(principal);

        // Fetch news related to the cinema owner
        List<News> listNews = newsService.listAllByCinemaOwnerID(cinemaOwnerID);

        // Fetch all movies (adjust as per your service method)
        List<Movie> movies = movieService.getAllMovies();

        // Fetch promotions for the cinema owner
        List<Promotions> promotions = promotionService.listAllByCinemaOwnerID(cinemaOwnerID);

        // Add fetched data to the model
        model.addAttribute("listNews", listNews);
        model.addAttribute("movies", movies);
        model.addAttribute("promotions", promotions);

        // Return the view name
        return "newlist";
    }
    private Integer getCinemaOwnerIDFromPrincipal(Principal principal) {
        Users user = userService.getUsersByEmail(principal.getName());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user.getUserId();
    }

    public static class Utility {
        public static String getSiteURL(HttpServletRequest request) {
            String siteURL = request.getRequestURL().toString();
            return siteURL.replace(request.getServletPath(), "");
        }
    }
}
