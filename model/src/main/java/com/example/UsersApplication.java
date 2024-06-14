package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.CRUD.service.UserService;
import com.example.mo.Users;

@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(UsersApplication.class, args);
            UserService userService = context.getBean(UserService.class);
			
			Users users = userService.getUsersById(2); 

			
			if (users != null) {
				System.out.println("ID: " + users.getUserId());
				System.out.println("Name: " + users.getUserName());
				System.out.println("Phone: " + users.getPhone());
				System.out.println("Email: " + users.getEmail());
				System.out.println("PassWord: " + users.getUserPassword());
				System.out.println("Location: " + users.getLocation());
				System.out.println("Birthdate: " + users.getBirthdate());
				System.out.println("UserRank: " + users.getUserRank());
				System.out.println("Memberpoints: " + users.getMemberPoints());
				System.out.println("UserType: " + users.getUserType());
				System.out.println("image: " + users.getProfileImageURL());
			} else {
				System.out.println("No user found with the provided ID.");
			}
	}

}
