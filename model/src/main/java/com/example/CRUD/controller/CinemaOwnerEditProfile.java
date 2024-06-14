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

import com.example.CRUD.service.PurchaseHistoryService;
import com.example.CRUD.service.UserService;
import com.example.mo.PurchaseHistory;
import com.example.mo.Users;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cinemaowner")
public class CinemaOwnerEditProfile {

    @Autowired
    private UserService userService;
    @Autowired
    private PurchaseHistoryService historyService;

    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

    @ModelAttribute
    public void commonUser(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            Users user = userService.getUsersByEmail(email);
            m.addAttribute("user", user);
        }
    }

    @GetMapping("/edit/profile")
    public String getUserProfile(Model model, Principal principal) {
        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/edit/upload-avatar")
    public String changeAvatar(Model model, @RequestParam("file") MultipartFile file, Principal principal)
            throws IOException {
        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);
        String originalFilename = file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDirectory, originalFilename);
        Files.write(fileNameAndPath, file.getBytes());
        user.setProfileImageURL(originalFilename);
        userService.updateUser(user);
        model.addAttribute("user", user);
        return "redirect:/cinemaowner/edit/profile";
    }

    @GetMapping("/edit/update-profile")
    public String showUpdateProfile(Model model, Principal principal) {
        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);
        model.addAttribute("user", user);
        return "update-profile";
    }

    @PostMapping("/edit/update-profile/save")
    public String updateProfile(@ModelAttribute Users user, Model model, Principal principal,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        String email = principal.getName();
        Users updateUser = userService.getUsersByEmail(email);

        boolean isUpdated = false;
        if (file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDirectory, originalFilename);
            Files.write(fileNameAndPath, file.getBytes());
            updateUser.setProfileImageURL(originalFilename);
            isUpdated = true;
        }
        if (user.getUserName() != null) {
            updateUser.setUserName(user.getUserName());
            isUpdated = true;
        }
        if (user.getEmail() != null) {
            updateUser.setEmail(user.getEmail());
            isUpdated = true;
        }
        if (user.getPhone() != null) {
            updateUser.setPhone(user.getPhone());
            isUpdated = true;
        }
        if (user.getBirthdate() != null) {
            updateUser.setBirthdate(user.getBirthdate());
            isUpdated = true;
        }
        if (user.getGender() != null) {
            updateUser.setGender(user.getGender());
            isUpdated = true;
        }
        if (user.getLocation() != null) {
            updateUser.setLocation(user.getLocation());
            isUpdated = true;
        }

        if (isUpdated) {
            userService.updateUser(updateUser);
            model.addAttribute("message", "Update successfully!");
        } else {
            model.addAttribute("error", "No information has been updated.");
        }

        model.addAttribute("user", updateUser);
        return "update-profile";
    }

    @GetMapping("/edit/change-password")
    public String showChangePasswordForm(Model model, Principal principal) {
        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);
        model.addAttribute("user", user);
        return "change-password";
    }

    @PostMapping("/edit/change-password/save")
    public String changePassword(@RequestParam("newPassword") String newPassword,
            @RequestParam("curPassword") String curPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model, Principal principal) {
        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);
        model.addAttribute("user", user);
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New password and confirm password do not match!");
            return "change-password";
        }
        boolean passwordChanged = userService.updatePassword(user.getUserId(), curPassword, newPassword);

        if (passwordChanged) {
            model.addAttribute("message", "Password changed successfully!");
        } else {
            model.addAttribute("error", "Current password is incorrect!");
        }
        return "change-password";
    }

    @GetMapping("/edit/history")
    public String getPurchaseHistory(Model model, Principal principal) {
        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);
        List<PurchaseHistory> purchaseHistories = historyService.getPurchaseHistoryById(user.getUserId());
        model.addAttribute("purchaseHistories", purchaseHistories);
        return "history";
    }
}