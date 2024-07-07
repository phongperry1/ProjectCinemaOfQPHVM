package com.example.CRUD.controller;

import com.example.mo.Movie;
import com.example.mo.Rating;
import com.example.mo.Users;
import com.example.CRUD.service.MovieService;
import com.example.CRUD.service.RatingService;
import com.example.CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/ratings")
public class RatingController {

    private final MovieService movieService;
    private final RatingService ratingService;
    private final UserService userService;

    @Autowired
    public RatingController(MovieService movieService, RatingService ratingService, UserService userService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @ModelAttribute
    public void addCommonAttributes(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Users user = userService.getUsersByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @PostMapping("/{movieID}")
    public String submitRating(@PathVariable Integer movieID,
                               @RequestParam("content") String content,
                               @RequestParam(value = "score", required = false) Integer score,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }
    
        if (score == null) {
            redirectAttributes.addFlashAttribute("message", "Bạn hãy đánh giá điểm.");
            return "redirect:/movie/book/" + movieID;
        }
    
        if (content.length() > 255) {
            redirectAttributes.addFlashAttribute("message", "Nội dung đánh giá không được vượt quá 255 ký tự.");
            return "redirect:/movie/book/" + movieID;
        }
    
        List<String> badwords = Arrays.asList("Con Cac", "cc", "ngu","dcm","cl","cac","cong san","phong","Quan");
        for (String word : badwords) {
            if (content.toLowerCase().contains(word.toLowerCase())) {
                redirectAttributes.addFlashAttribute("message", "Nội dung đánh giá chứa từ cấm.");
                return "redirect:/movie/book/" + movieID;
            }
        }
    
        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);
        Rating existingRating = ratingService.getRatingByUserAndMovie(user, movieID);
if (existingRating != null) {
            redirectAttributes.addFlashAttribute("message", "Bạn đã đánh giá cho bộ phim này rồi.");
        } else {
            Movie movie = movieService.getMovieById(movieID);
            if (movie == null) {
                return "redirect:/error";
            }
    
            Rating rating = new Rating();
            rating.setMovie(movie);
            rating.setUser(user);
            rating.setContent(content);
            rating.setScore(score);
    
            try {
                ratingService.saveRating(rating);
            } catch (DataIntegrityViolationException e) {
                redirectAttributes.addFlashAttribute("message", "Nội dung đánh giá không được vượt quá 255 ký tự.");
                return "redirect:/movie/book/" + movieID;
            }
        }
    
        return "redirect:/movie/book/" + movieID;
    }
    

    
   
    @PostMapping("/delete/{ratingId}")
    public String deleteRating(@PathVariable Integer ratingId,
                               Principal principal,
                               Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);
        Rating rating = ratingService.getRatingById(ratingId);

        if (rating != null && rating.getUser().equals(user)) {
            Integer movieId = rating.getMovie().getMovieID();
            ratingService.deleteRating(ratingId);
            movieService.updateAverageRating(movieId);
        } else {
            model.addAttribute("error", "You can only delete your own ratings.");
        }

        return "redirect:/movie/book/" + rating.getMovie().getMovieID();
    }
}