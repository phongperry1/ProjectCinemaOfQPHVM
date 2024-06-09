package com.example.Quan.mo;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Sampson Alfred
 */
@Controller
@RequestMapping("/cinema-owner")
public class CinemaOwnerController {
    @PreAuthorize("hasRole('ROLE_CINEMA_OWNER')")
    @GetMapping("/manage-cinema")
    public String manageCinema(Model model) {
        // Logic to manage cinema
        return "manage-cinema";
    }

    // Other cinema owner functionalities
}
