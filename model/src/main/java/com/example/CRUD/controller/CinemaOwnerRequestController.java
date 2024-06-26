package com.example.CRUD.controller;

    
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.CRUD.service.CinemaOwnerRequestService;
import com.example.CRUD.service.UserService;
import com.example.mo.CinemaOwnerRequest;
import com.example.mo.Users;

@Controller
public class CinemaOwnerRequestController {
    @Autowired
    private final CinemaOwnerRequestService cinemaOwnerRequestService;
    private final UserService userService;


    public CinemaOwnerRequestController(UserService userService, CinemaOwnerRequestService cinemaOwnerRequestService) {
        this.userService = userService;
        this.cinemaOwnerRequestService = cinemaOwnerRequestService;
    }
    @GetMapping("/register-cinemaowner")
    public String showRegistrationPage() {
        return "register-cinema-owner";
    }
    
    @PostMapping("/register-cinemaowner/save")
public String createCinemaOwnerRequest(@ModelAttribute("cinemaOwnerRequest") CinemaOwnerRequest cinemaOwnerRequest, Principal principal, Model model) {
    String email = principal.getName();
    Users currentUser = userService.getUsersByEmail(email);
    if (currentUser.getRole().equals("ROLE_CINEMA_OWNER")) {
        model.addAttribute("errorMessage", "You are already a Cinema Owner. You cannot register again.");
        return "register-cinema-owner";
    }
    if (cinemaOwnerRequestService.isUserRegisteredAsCinemaOwner(currentUser.getUserId())) {
        model.addAttribute("errorMessage", "You have already registered as a Cinema Owner. Please wait for the admin to review and approve your request.");
        return "register-cinema-owner";
    }
    cinemaOwnerRequest.setUsers(currentUser);
    cinemaOwnerRequestService.saveCinemaOwnerRequest(cinemaOwnerRequest);
    model.addAttribute("successMessage", "Your Cinema Owner request has been successfully submitted.");
    return "register-cinema-owner";
}

    @GetMapping("/show-cinema-owner-request")
    public String getAllCinemaOwnerRequests(Model model) {
        List<CinemaOwnerRequest> cinemaOwnerRequests = cinemaOwnerRequestService.getAllCinemaOwnerRequests();
        model.addAttribute("cinemaOwnerRequests", cinemaOwnerRequests);
        return "cinema-owner-request";
    }
    @PostMapping("/{id}/approve")
    public String approveCinemaOwnerRequest(@PathVariable int id) {
        cinemaOwnerRequestService.approveCinemaOwnerRequest(id);

        return "redirect:/show-cinema-owner-request";
    }

    @PostMapping("/{id}/reject")
    public String rejectCinemaOwnerRequest(@PathVariable int id) {
        cinemaOwnerRequestService.rejectCinemaOwnerRequest(id);
        return "redirect:/show-cinema-owner-request";
    }
}
