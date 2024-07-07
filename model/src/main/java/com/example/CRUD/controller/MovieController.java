package com.example.CRUD.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
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
import com.example.CRUD.service.RatingService;
import com.example.CRUD.service.TheaterService;
import com.example.CRUD.service.UserService;
import com.example.mo.Movie;
import com.example.mo.Rating;
import com.example.mo.Theater;
import com.example.mo.Users;

@Controller
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;
    private final RatingService ratingService;
    private final UserService userService;
    @Autowired
    private TheaterService theaterService;

    public MovieController(MovieService movieService, UserService userService, RatingService ratingService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllMovies(Model model, Principal principal) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "movie";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model, Principal principal) {
        Integer cinemaOwnerID = getCinemaOwnerIDFromPrincipal(principal);
        List<Theater> listTheater = theaterService.listAllByCinemaOwnerID(cinemaOwnerID);
        model.addAttribute("listTheater", listTheater);
        model.addAttribute("movie", new Movie());
        return "movie-form";
    }

    @PostMapping("/create")
    public String createMovie(@ModelAttribute Movie movie, @RequestParam("imageFile") MultipartFile imageFile,
                              RedirectAttributes redirectAttributes,Principal principal) {
        movie.setRatingCount(0);
        movie.setAverageRating(0.0);
        movie.setCinemaOwnerID(getCinemaOwnerIDFromPrincipal(principal));
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
    public String showEditForm(@PathVariable Integer id, Model model, Principal principal) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            Integer cinemaOwnerID = getCinemaOwnerIDFromPrincipal(principal);
            List<Theater> listTheater = theaterService.listAllByCinemaOwnerID(cinemaOwnerID);
            model.addAttribute("movie", movie);
            model.addAttribute("listTheater", listTheater);
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
    public String getAllMoviesForHome(Model model, Principal principal) {
        Users user = userService.getUserByUserName(principal.getName());
        model.addAttribute("user", user);

        List<Movie> movies = movieService.getAllMovies();
        List<Movie> simplifiedMovies = movies.stream()
                .map(m -> {
                    Movie simplifiedMovie = new Movie();
                    simplifiedMovie.setTitle(m.getTitle());
                    simplifiedMovie.setAddress(m.getAddress());
                    return simplifiedMovie;
                })
                .collect(Collectors.toList());
        model.addAttribute("movies", simplifiedMovies);
        return "home";
    }

    @PostMapping("/vote/{id}")
    public String voteForMovie(@PathVariable Integer id, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            Users user = userService.getUsersByEmail(principal.getName());
            movieService.voteForMovie(id, user.getUserId());
            redirectAttributes.addFlashAttribute("message", "Vote successful!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/movie";
    }

    @GetMapping("/book/{movieId}")
    public String getMovieRatings(@PathVariable Integer movieId,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                  Model model, Principal principal) {
        Movie movie = movieService.getMovieById(movieId);
        if (movie == null) {
            return "redirect:/error";
        }

        List<Rating> ratings = ratingService.getAllRatingsByMovieId(movieId);

        int start = Math.min(page * size, ratings.size());
        int end = Math.min((page + 1) * size, ratings.size());

        List<Rating> paginatedRatings = ratings.subList(start, end);

        model.addAttribute("movie", movie);
        model.addAttribute("ratings", paginatedRatings);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) ratings.size() / size));
        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);

        model.addAttribute("user", user);
        return "book";
    }

    @GetMapping("/search")
    public String searchMovies(@RequestParam(name = "keyword", required = false) String keyword, Model model, Principal p) {
        List<Movie> allMovies = movieService.getAllMovies();
        List<Movie> comingSoonMovies = movieService.getAllComingSoonMovies();
        List<Movie> movies = allMovies;
        String email = p.getName();
        Users user = userService.getUsersByEmail(email);

        if (keyword != null && !keyword.isEmpty()) {
            movies = movies.stream().filter(m -> m.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());

            if (movies.isEmpty()) {
                model.addAttribute("message", "Không tìm thấy phim nào");
                movies = allMovies; // Hiển thị tất cả phim nếu không tìm thấy phim nào
            }
        }

        List<Movie> simplifiedMovies = movies.stream()
                .map(m -> {
                    Movie simplifiedMovie = new Movie();
                    simplifiedMovie.setMovieID(m.getMovieID());
                    simplifiedMovie.setTitle(m.getTitle());
                    simplifiedMovie.setAddress(m.getAddress());
                    return simplifiedMovie;
                })
                .collect(Collectors.toList());
        model.addAttribute("user", user);
        model.addAttribute("comingSoonMovies", comingSoonMovies);
        model.addAttribute("movies", simplifiedMovies);
        return "home"; // Trả về view 'home' để hiển thị kết quả tìm kiếm
    }

    @GetMapping("/general/genre")
    public String searchByGenre(@RequestParam("genre") String genre, Model model, Principal p) {
        String email = p.getName();
        Users user = userService.getUsersByEmail(email);
        List<Movie> moviesByGenre = movieService.getMoviesByGenre(genre);
        List<Movie> comingSoonMovies = movieService.getAllComingSoonMovies();
        model.addAttribute("user", user);
        model.addAttribute("comingSoonMovies", comingSoonMovies);
        model.addAttribute("movies", moviesByGenre);
        return "home";
    }

    private Integer getCinemaOwnerIDFromPrincipal(Principal principal) {
        Users user = userService.getUsersByEmail(principal.getName());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user.getUserId(); // Ensure this returns the correct ID for cinema owner
    }
}
