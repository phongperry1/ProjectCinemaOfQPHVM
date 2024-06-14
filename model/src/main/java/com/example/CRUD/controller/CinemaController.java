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

@Controller
@RequestMapping("/cinemaowner")
public class CinemaController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserService userService;

	public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			Users user = userRepo.findByEmail(email);
			m.addAttribute("cinemaowner", user);
		}
	}

	@GetMapping("/homecinemaowner")
	public String homeCinemaOwner() {
		return "homecinemaowner";
	}

	@GetMapping("/profilecinemaowner")
	public String getProfile(Model model, Principal principal) {
		String email = principal.getName();
		Users user = userService.getUsersByEmail(email);
		model.addAttribute("cinemaowner", user);
		return "profilecinemaowner";
	}

	@PostMapping("/upload-avatar")
	public String changeAvatar(Model model, @RequestParam("file") MultipartFile file, Principal principal)
			throws IOException {
		String email = principal.getName();
		Users user = userService.getUsersByEmail(email);
		String originalFilename = file.getOriginalFilename();
		Path fileNameAndPath = Paths.get(uploadDirectory, originalFilename);
		Files.write(fileNameAndPath, file.getBytes());
		user.setProfileImageURL(originalFilename);
		userService.updateUser(user);
		model.addAttribute("cinemaowner", user);
		return "redirect:/cinemaowner/profilecinemaowner";
	}

	@GetMapping("/update-profilecinemaowner")
	public String showUpdateProfile(Model model, Principal principal) {
		String email = principal.getName();
		Users user = userService.getUsersByEmail(email);
		model.addAttribute("cinemaowner", user);
		return "update-profilecinemaowner";
	}

	@PostMapping("/update-profilecinemaowner/save")
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

		model.addAttribute("cinemaowner", updateUser);
		return "update-profilecinemaowner";
	}

	@GetMapping("/change-passwordcinemaowner")
	public String showChangePasswordForm(Model model, Principal principal) {
		String email = principal.getName();
		Users user = userService.getUsersByEmail(email);
		model.addAttribute("cinemaowner", user);
		return "change-passwordcinemaowner";
	}

	@PostMapping("/change-passwordcinemaowner/save")
	public String changePassword(@RequestParam("newPassword") String newPassword,
			@RequestParam("curPassword") String curPassword,
			@RequestParam("confirmPassword") String confirmPassword,
			Model model, Principal principal) {
		String email = principal.getName();
		Users user = userService.getUsersByEmail(email);
		model.addAttribute("cinemaowner", user);
		if (!newPassword.equals(confirmPassword)) {
			model.addAttribute("error", "New password and confirm password do not match!");
			return "change-passwordcinemaowner";
		}
		boolean passwordChanged = userService.updatePassword(user.getUserId(), curPassword, newPassword);

		if (passwordChanged) {
			model.addAttribute("message", "Password changed successfully!");
		} else {
			model.addAttribute("error", "Current password is incorrect!");
		}
		return "change-passwordcinemaowner";
	}
}