package com.example.CRUD.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.mo.Users;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;

import com.example.CRUD.Repository.UserRepository;

@Service("userServiceImpl")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

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
        if (existingUser != null && passwordEncoder.matches(currentPassword, existingUser.getUserPassword())) {
            existingUser.setUserPassword(passwordEncoder.encode(newPassword));
            userRepository.save(existingUser);
            return true; // Mật khẩu đã được cập nhật thành công
        }
        return false; // Cập nhật mật khẩu thất bại
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

    public Users saveUser(Users user, String url) {
        String password = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(password);
        user.setRole("ROLE_USER");
        user.setStatus(false);
        user.setVerificationCode(UUID.randomUUID().toString());

        Users newuser = userRepository.save(user);

        if (newuser != null) {
            sendEmail(newuser, url);
        }

        return newuser;
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

    public void removeSessionMessage() {
        HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
                .getSession();
        session.removeAttribute("msg");
    }

    public void updateResetPasswordToken(String token, String email) /* throws UserNotFoundException */ {

        Users user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            System.out.println("Could not find any user with the email " + email);
        }
    }

    public Users getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(Users user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setUserPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
