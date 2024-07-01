package com.example.CRUD.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.CRUD.service.TicketService;
import com.example.CRUD.service.UserService;
import com.example.mo.Ticket;
import com.example.mo.Users;

import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService ser;
    @Autowired
    private TicketService ticketService;

    @ModelAttribute
    public void commonUser(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            Users user = ser.getUsersByEmail(email);
            m.addAttribute("user", user);
        }
    }

    @GetMapping("/home")
    public String profile() {
        return "home";
    }

    @GetMapping("/profile")
    public String getProfile() {
        return "profile";
    }

    @PostMapping("/upload-avatar")
    public String changeAvatar(Model model, @RequestParam("file") MultipartFile file, Principal principal)
            throws IOException {
        String email = principal.getName();
        Users user = ser.getUsersByEmail(email);
        String originalFilename = file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(System.getProperty("user.dir") + "/uploads", originalFilename);
        Files.write(fileNameAndPath, file.getBytes());
        user.setProfileImageURL(originalFilename);
        ser.updateUser(user);
        model.addAttribute("user", user);
        return "redirect:/user/profile";
    }

    @GetMapping("/update-profile")
    public String showUpdateProfile(Model model, Principal principal) {
        String email = principal.getName();
        Users user = ser.getUsersByEmail(email);
        model.addAttribute("user", user);
        return "update-profile";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model, Principal principal) {
        String email = principal.getName();
        Users user = ser.getUsersByEmail(email);
        model.addAttribute("user", user);
        return "change-password";
    }

    @GetMapping("/mytickets")
    public String ShowMyTickets(Model model, Principal principal) {
        String email = principal.getName();
        Users user = ser.getUsersByEmail(email);
        int userId = user.getUserId();
        List<Ticket> tickets = ticketService.getTicketsByUserId(userId);
        model.addAttribute("tickets", tickets);
        return "mytickets";
    }
}