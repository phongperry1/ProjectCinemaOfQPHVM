package com.example.CRUD.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.mo.Users;
import com.example.CRUD.Repository.UserRepository;

import java.util.List;

@Component
public class GuestUserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Remove this if not necessary for guest user creation

    @Override
    public void run(String... args) throws Exception {
        // Check if there are any users with role "GUEST"
        List<Users> existingGuestUsers = userRepository.findByRole("GUEST");
        if (existingGuestUsers.isEmpty()) {
            // No guest users found, create a new one
            Users guestUser = new Users();
            guestUser.setUserName("Guest User");
            guestUser.setRole("GUEST"); // Set the role for the user to "GUEST"
            // Encode password if needed (for example, for actual users)
            // guestUser.setPassword(passwordEncoder.encode("guestpassword"));
            userRepository.save(guestUser);
            System.out.println("Created guest user with role GUEST");
        } else if (existingGuestUsers.size() == 1) {
            // One guest user found, use it if needed
            Users guestUser = existingGuestUsers.get(0);
            System.out.println("Guest user with role GUEST already exists: " + guestUser.getUserName());
        } else {
            // More than one guest user found, handle accordingly
            for (Users guestUser : existingGuestUsers) {
                System.out.println("Guest user with role GUEST: " + guestUser.getUserName());
            }
            // You may want to handle this situation based on your application's logic
        }
    }
}