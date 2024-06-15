package com.example.CRUD.controller;

import com.example.mo.Movie;
import com.example.mo.Promotions;
import com.example.mo.Users;
import com.example.CRUD.Repository.UserRepository;
import com.example.CRUD.service.MovieService;
import com.example.CRUD.service.PromotionsService;
import com.example.CRUD.service.UserService;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import net.bytebuddy.utility.RandomString;

@Controller
public class HomeController {

    private final MovieService movieService;
    private final PromotionsService promotionsService;
    private final UserService userService;
    private final UserRepository userRepo;

    @Autowired
    public HomeController(MovieService movieService, PromotionsService promotionsService, UserService userService,
            UserRepository userRepo) {
        this.movieService = movieService;
        this.promotionsService = promotionsService;
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @PostMapping("/userLogin")
    public String login(@ModelAttribute("user") Users user, HttpSession session, Model model) {
        Users authenticatedUser = userService.authenticate(user.getEmail(), user.getUserPassword());
        if (authenticatedUser != null) {
            session.setAttribute("user", authenticatedUser);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/home")
    public String showHomePage(Model model, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        List<Movie> movies = movieService.getAllMovies();
        List<Movie> simplifiedMovies = movies.stream()
                .map(m -> {
                    Movie simplifiedMovie = new Movie();
                    simplifiedMovie.setTitle(m.getTitle());
                    simplifiedMovie.setShowTime(m.getShowTime());
                    simplifiedMovie.setAddress(m.getAddress());
                    return simplifiedMovie;
                })
                .collect(Collectors.toList());
        model.addAttribute("movies", simplifiedMovies);
        List<Promotions> listPromotions = promotionsService.listAll();
        model.addAttribute("listPromotions", listPromotions);
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/home/{id}")
    public String getMovieForHome(@PathVariable Integer id, Model model) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            model.addAttribute("movie", movie);
            return "home";
        }
        return "redirect:/home";
    }

    @ModelAttribute
    public void commonUser(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            Users user = userRepo.findByEmail(email);
            m.addAttribute("user", user);
        }
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute Users user, HttpSession session, Model m, HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        url = url.replace(request.getServletPath(), "");
        Users u = userService.saveUser(user, url);
        if (u != null) {
            session.setAttribute("msg", "Register successfully");
        } else {
            session.setAttribute("msg", "Something wrong server");
        }
        return "redirect:/register";
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code, Model m) {
        boolean f = userService.verifyAccount(code);

        if (f) {
            m.addAttribute("msg", "Sucessfully your account is verified");
        } else {
            m.addAttribute("msg", "may be your vefication code is incorrect or already veified ");
        }

        return "message";
    }

    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/resetPassword?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "forgotPassword";
    }

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("dhquan235@shopme.com", "Shopme Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordForm(@RequestParam(value = "token") String token, Model model) {
        Users user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message"; // Assuming you have a message.html template
        }

        return "resetPassword";
    }

    @PostMapping("/resetPassword")
    public String processResetPassword(@RequestParam(value = "token") String token,
            @RequestParam(value = "password") String password,
            Model model) {
        Users user = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message"; // Assuming you have a message.html template
        } else {
            userService.updatePassword(user, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "login"; // Assuming you have a message.html template
    }

    public class Utility {
        public static String getSiteURL(HttpServletRequest request) {
            String siteURL = request.getRequestURL().toString();
            return siteURL.replace(request.getServletPath(), "");
        }
    }

}