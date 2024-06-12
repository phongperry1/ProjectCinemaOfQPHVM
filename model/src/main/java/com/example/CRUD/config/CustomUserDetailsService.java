package com.example.CRUD.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.mo.Users;
import com.example.CRUD.Repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepo.findByEmail(username);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        } else {
            return new CustomUser(user);
        }

    }

}
