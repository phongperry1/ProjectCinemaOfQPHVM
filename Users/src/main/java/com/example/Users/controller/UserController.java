package com.example.Users.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Users.entity.Users;

import com.example.Users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService ser;
    
    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
    
    @GetMapping("/profile")
    public String getUserProfile(Model model) {
        Users user = ser.getUsersById(1);
        model.addAttribute("user1", user);    
        return "profile";
    }

    @PostMapping("/profile/upload-avatar")
    public String changeAvatar(Model model, @RequestParam("file") MultipartFile file) throws IOException{
        Users user = ser.getUsersById(1);
        String originalFilename = file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDirectory, originalFilename);
        Files.write(fileNameAndPath, file.getBytes());
        user.setProfileImageUrl(originalFilename);
        ser.updateUser(user);
        model.addAttribute("user1", user);  
        return "redirect:/profile";
    }
    
    

    @GetMapping("/update-profile")
    public String showUpdateProfile(@ModelAttribute Users user, Model model) {
        Users users = ser.getUsersById(1);
        model.addAttribute("user2", users);
        return "update-profile";
    }

    @GetMapping("/update-profile/save")
    public String updateProfile(@ModelAttribute Users user, Model model) {
        Users updateUser = ser.getUsersById(1);
        
        boolean isUpdated = false;
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
            ser.updateUser(updateUser);
            model.addAttribute("message", "Update successfully!");
        } else {
            model.addAttribute("error", "No information has been updated.");
        }

        model.addAttribute("user2", updateUser);
        return "update-profile";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(@ModelAttribute Users user, Model model) {
        Users users = ser.getUsersById(1);
        model.addAttribute("user3", users);   
        return "change-password";
    }


    @GetMapping("/change-password/save")
    public String changePassword(@RequestParam("newPassword") String newPassword,
                                 @RequestParam("curPassword") String curPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 @ModelAttribute Users user, Model model) {
        Users users = ser.getUsersById(1);
        model.addAttribute("user3", users);
            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("error", "New password and confirm password do not match!");
                return "change-password";
            }
        // Thực hiện cập nhật mật khẩu từ dữ liệu gửi từ form
        boolean passwordChanged = ser.updatePassword(1, curPassword, newPassword);
    
        // Xử lý kết quả và chuyển hướng đến trang tương ứng
        if (passwordChanged) {
            model.addAttribute("message", "Password changed successfully!");
        } else {
            model.addAttribute("error", "Current password is incorrect!");
        }
        return "change-password";
    }
    
}
