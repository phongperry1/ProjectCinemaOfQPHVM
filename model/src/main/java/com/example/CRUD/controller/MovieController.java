package com.example.CRUD.controller;

import com.example.CRUD.service.MovieService;
import com.example.mo.Movie;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public String getAllMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movie", movies);
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
                // Lưu tệp tải lên vào thư mục cục bộ
                String uploadDir = System.getProperty("user.dir") + "/uploads/";
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }
                String fileName = imageFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.write(filePath, imageFile.getBytes());
                movie.setAddress("/uploads/" + fileName); // Lưu đường dẫn ảnh vào thuộc tính address
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("message", "Không thể tải lên tệp ảnh.");
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
                    // Lưu tệp tải lên vào thư mục cục bộ
                    String uploadDir = System.getProperty("user.dir") + "/uploads/";
                    File uploadDirFile = new File(uploadDir);
                    if (!uploadDirFile.exists()) {
                        uploadDirFile.mkdirs();
                    }
                    String fileName = imageFile.getOriginalFilename();
                    Path filePath = Paths.get(uploadDir + fileName);
                    Files.write(filePath, imageFile.getBytes());
                    movie.setAddress("/uploads/" + fileName); // Lưu đường dẫn ảnh vào thuộc tính address
                } catch (IOException e) {
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("message", "Không thể tải lên tệp ảnh.");
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
                    // simplifiedMovie.setShowTime(m.getShowTime());
                    simplifiedMovie.setAddress(m.getAddress());
                    return simplifiedMovie;
                })
                .collect(Collectors.toList());
        model.addAttribute("movies", simplifiedMovies);
        return "home";
    }
    @GetMapping("/book/{id}")
public String showMovieDetails(@PathVariable("movieID") Integer id, Model model) {
    Movie movie = movieService.getMovieById(id);
    if (movie != null) {
        model.addAttribute("movie", movie);
        return "book"; // Tên của trang HTML cho chi tiết phim
    }
    return "redirect:/movie";
}


}
