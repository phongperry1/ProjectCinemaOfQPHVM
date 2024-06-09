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
@RequestMapping("/admin")
public class AdminController {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/manage-users")
    public String manageUsers(Model model) {
        // Logic to manage users
        return "manage-users";
    }

    // Other admin functionalities
}
