package com.example.CRUD.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.CRUD.Repository.UserRepository;
import com.example.mo.Users;


@Controller
@RequestMapping("/cinemaowner")
public class CinemaController {

	@Autowired
	private UserRepository userRepo;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			Users user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}
	}

	@GetMapping("/homecinemaowner")
	public String profile() {
		return "homecinemaowner";
	}

	@GetMapping("/profile")
	public String getMethodName(@RequestParam String param) {
		return "profile";
	}
	@GetMapping("/update-profile/save")
	public String updateprofile(@RequestParam String param) {
		return "update-profile";
	}
	
}
