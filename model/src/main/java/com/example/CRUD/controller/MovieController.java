package com.example.CRUD.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.CRUD.service.MovieService;
import com.example.CRUD.service.ShowtimeService;
import com.example.mo.Movie;
import com.example.mo.Showtime;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ShowtimeService showtimeService;

    @GetMapping
    public String getAllMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "movie";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "movie-form";
    }

    @PostMapping("/create")
    public String createMovie(@ModelAttribute Movie movie, @RequestParam("imageFile") MultipartFile imageFile,
                              RedirectAttributes redirectAttributes) {
        movie.setRatingCount(0);
        movie.setAverageRating(0.0);
        if (!imageFile.isEmpty()) {
            try {
                // Save uploaded file to local directory
                String uploadDir = System.getProperty("user.dir") + "/uploads/";
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }
                String fileName = imageFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.write(filePath, imageFile.getBytes());
                movie.setAddress("/uploads/" + fileName); // Save image path to address attribute
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("message", "Cannot upload image file.");
                return "redirect:/movie/new";
            }
        }
        movieService.saveMovie(movie);
        return "redirect:/movie";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            model.addAttribute("movie", movie);
            return "movie-form";
        }
        return "redirect:/movie";
    }

    @PostMapping("/update/{id}")
    public String updateMovie(@PathVariable Integer id, @ModelAttribute Movie movieDetails,
                              @RequestParam("imageFile") MultipartFile imageFile, RedirectAttributes redirectAttributes) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            if (!imageFile.isEmpty()) {
                try {
                    // Save uploaded file to local directory
                    String uploadDir = System.getProperty("user.dir") + "/uploads/";
                    File uploadDirFile = new File(uploadDir);
                    if (!uploadDirFile.exists()) {
                        uploadDirFile.mkdirs();
                    }
                    String fileName = imageFile.getOriginalFilename();
                    Path filePath = Paths.get(uploadDir + fileName);
                    Files.write(filePath, imageFile.getBytes());
                    movie.setAddress("/uploads/" + fileName); // Save image path to address attribute
                } catch (IOException e) {
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("message", "Cannot upload image file.");
                    return "redirect:/movie/edit/" + id;
                }
            }
            movie.updateDetails(movieDetails);
            movieService.saveMovie(movie);
        }
        return "redirect:/movie";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
        return "redirect:/movie";
    }

    @GetMapping("/home/{id}")
    public String getMovieForHome(@PathVariable Integer id, Model model) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            model.addAttribute("movie", movie);
            return "home";
        }
        return "redirect:/movie";
    }

    @GetMapping("/home")
    public String getAllMoviesForHome(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        List<Movie> simplifiedMovies = movies.stream()
                .map(m -> {
                    Movie simplifiedMovie = new Movie();
                    simplifiedMovie.setTitle(m.getTitle());
                    simplifiedMovie.setGenre(m.getGenre());
                    simplifiedMovie.setAddress(m.getAddress());
                    return simplifiedMovie;
                })
                .collect(Collectors.toList());
        model.addAttribute("movies", simplifiedMovies);
        return "home";
    }

    @GetMapping("/book/{id}")
    public String showMovieDetails(@PathVariable("id") Integer id, Model model) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            List<Showtime> showtimes = showtimeService.getShowtimesByMovieID(id);
            System.out.println("Showtimes for movie ID " + id + ": " + showtimes);
            model.addAttribute("movie", movie);
            model.addAttribute("listShowtime", showtimes);
            return "book"; // Ensure "book.html" exists in templates
        }
        return "redirect:/movie";
    }
}
