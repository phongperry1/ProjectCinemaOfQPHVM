package com.example.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Repository.UserRepository;

/**
 * @author Sampson Alfred
 */
@Service
@RequiredArgsConstructor
public class EndToEndUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .filter(user -> user.isStatus() == true) // Assuming 1 means active status
                .map(EndToEndUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found or inactive"));
    }
}