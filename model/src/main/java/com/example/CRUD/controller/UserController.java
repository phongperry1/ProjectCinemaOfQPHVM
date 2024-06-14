package com.example.CRUD.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.CRUD.Repository.UserRepository;
import com.example.CRUD.service.UserService;
import com.example.mo.Users;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepo;
    private UserService ser;
    
    @ModelAttribute
    public void commonUser(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            Users user = userRepo.findByEmail(email);
            m.addAttribute("user", user);
        }

    }

    @GetMapping("/home")
    public String profile() {
        return "home";
    }

    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
    @GetMapping("/profile")
    public String getUserProfile(Model model, HttpSession session) {
        // Users user = (Users) session.getAttribute("user");
        Users user = ser.getUsersById(2);
        model.addAttribute("user", user);    
        return "profile";
    }

    @PostMapping("/profile/upload-avatar")
    public String changeAvatar(Model model, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException{
        Users user = (Users) session.getAttribute("user");
        String originalFilename = file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDirectory, originalFilename);
        Files.write(fileNameAndPath, file.getBytes());
        user.setProfileImageURL(originalFilename);
        ser.updateUser(user);
        model.addAttribute("user", user);  
        return "redirect:/profile";
    }
    
    

    @GetMapping("/update-profile")
    public String showUpdateProfile(@ModelAttribute Users user, Model model, HttpSession session) {
        Users users = (Users) session.getAttribute("user");
        model.addAttribute("user", users);
        return "update-profile";
    }

    @GetMapping("/update-profile/save")
    public String updateProfile(@ModelAttribute Users user, Model model, HttpSession session) {
        Users updateUser = (Users) session.getAttribute("user");
        
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

        model.addAttribute("user", updateUser);
        return "update-profile";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(@ModelAttribute Users user, Model model, HttpSession session) {
        Users users = (Users) session.getAttribute("user");
        model.addAttribute("user", users);   
        return "change-password";
    }


    @GetMapping("/change-password/save")
    public String changePassword(@RequestParam("newPassword") String newPassword,
                                 @RequestParam("curPassword") String curPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 @ModelAttribute Users user, Model model, HttpSession session) {
        Users users = (Users) session.getAttribute("user");
        model.addAttribute("user", users);
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
