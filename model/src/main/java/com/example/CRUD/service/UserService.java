package com.example.CRUD.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.mo.Users;
import com.example.CRUD.Repository.UserRepository;
import com.example.CRUD.controller.InsufficientBalanceException;

import jakarta.mail.internet.MimeMessage;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    public Users updateUser(Users users) {
        Users existingUser = userRepository.findById(users.getUserId()).orElse(null);
        if (existingUser != null) {
            existingUser.setUserName(users.getUserName());
            existingUser.setEmail(users.getEmail());
            existingUser.setLocation(users.getLocation());
            existingUser.setPhone(users.getPhone());
            existingUser.setBirthdate(users.getBirthdate());
            existingUser.setProfileImageURL(users.getProfileImageURL());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public Users authenticate(String email, String password) {
        Users user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getUserPassword())) {
            return user;
        }
        return null;
    }

    public boolean updatePassword(int userId, String currentPassword, String newPassword) {
        Users existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            if (passwordEncoder.matches(currentPassword, existingUser.getUserPassword())) {
                existingUser.setUserPassword(passwordEncoder.encode(newPassword));
                userRepository.save(existingUser);
                return true; // Password updated successfully
            }
        }
        return false; // Password update failed
    }

    public List<Users> getUsers() {
        return userRepository.findAll();
    }

    public Users getUsersById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public Users getUsersByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Users getUserByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    public Users saveUser(Users user, String url) {
        String password = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(password);
        user.setRole("ROLE_USER");
        user.setStatus(false);
        user.setVerificationCode(UUID.randomUUID().toString());

        Users newUser = userRepository.save(user);

        if (newUser != null) {
            sendEmail(newUser, url);
        }

        return newUser;
    }

    public void sendEmail(Users user, String url) {
        String from = "dhquan235@gmail.com";
        String to = user.getEmail();
        String subject = "Account Verification";
        String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "Becoder";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(from, "Becoder");
            helper.setTo(to);
            helper.setSubject(subject);

            content = content.replace("[[name]]", user.getUserName());
            String siteUrl = url + "/verify?code=" + user.getVerificationCode();
            content = content.replace("[[URL]]", siteUrl);

            helper.setText(content, true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verifyAccount(String verificationCode) {
        Users user = userRepository.findByVerificationCode(verificationCode);
        if (user == null) {
            return false;
        } else {
            user.setStatus(true);
            user.setVerificationCode(null);
            userRepository.save(user);
            return true;
        }
    }

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any user with the email " + email);
        }
    }

    public Users getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(Users user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setUserPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    public Users getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // Method for logging in a user
    public Users login(String email, String password) {
        Users user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getUserPassword())) {
            return user;
        } else {
            return null;
        }
    }

    // Methods to deposit and withdraw money
    public void deposit(int userId, Double amount) {
        Users user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (amount > 0) {
            user.deposit(amount);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
    }

    public void withdraw(int userId, Double amount) {
        Users user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (amount > 0 && user.getVirtualWallet() >= amount) {
            user.withdraw(amount);
            userRepository.save(user);
        } else {
            throw new InsufficientBalanceException("Insufficient balance or invalid amount.");
        }
    }
}
