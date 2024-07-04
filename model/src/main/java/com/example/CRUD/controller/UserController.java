package com.example.CRUD.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    @ModelAttribute
    public void commonUser(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            Users user = userService.getUsersByEmail(email);
            model.addAttribute("user", user);
        }
    }

    @GetMapping("/home")
    public String profile() {
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
}
