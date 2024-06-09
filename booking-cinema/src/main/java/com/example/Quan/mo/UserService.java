package com.example.Quan.mo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Quan.registration.RegistrationRequest;
import com.example.Quan.registration.token.VerificationTokenService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Sampson Alfred
 */
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users registerUser(RegistrationRequest registration) {
        var user = new Users(registration.getUserName(),
                registration.getEmail(),
                passwordEncoder.encode(registration.getPassword()),
                Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

    @Override
    public Optional<Users> findById(Integer userID) {
        return userRepository.findById(userID);
    }

    @Transactional
    public void updateUserStatus(Integer userID, int status) {
        Optional<Users> userOptional = userRepository.findById(userID);
        userOptional.ifPresent(user -> {
            user.setStatus(status);
            userRepository.save(user);
        });
    }
}
