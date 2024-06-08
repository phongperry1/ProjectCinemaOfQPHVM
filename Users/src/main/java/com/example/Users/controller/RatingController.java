// package com.example.Users.controller;

// import java.util.List;
// import java.util.ArrayList;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.example.Users.entity.Rating;
// import com.example.Users.entity.Users;
// import com.example.Users.repository.RatingRepository;
// import com.example.Users.service.RatingService;

// import ch.qos.logback.core.model.Model;

// @Controller
// public class RatingController {

//     @Autowired
//     private static RatingService ratingService;

//     @GetMapping("/MovieDetail")
//     public String getAllRatings(Model model) {
//         List<Rating> listRatings = ratingService.getAllRatings();
//         ((RedirectAttributes) model).addAttribute("listRatings", listRatings);
//         return "MovieDetail";
//     }


// }
