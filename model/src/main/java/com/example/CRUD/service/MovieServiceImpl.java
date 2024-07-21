package com.example.CRUD.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.MovieRepository;
import com.example.CRUD.Repository.RatingRepository;
import com.example.CRUD.Repository.UserRepository;
import com.example.mo.Movie;
import com.example.mo.Rating;
import com.example.mo.Users;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public MovieServiceImpl (MovieRepository movieRepository, UserRepository userRepository,RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

  
    @Override
    public Movie getMovieById(Integer movieID) {
        return movieRepository.findById(movieID).orElse(null);
    }

    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(Integer id) {
        movieRepository.deleteById(id);
    }
    public void voteForMovie(Integer movieId, Integer userId) {
        Movie movie = getMovieById(movieId);
        Users user = userRepository.findById(userId).orElse(null);

        if (movie == null || user == null) {
            throw new RuntimeException("Movie or User not found");
        }

        if (movie.getCinemaOwnerVotes().contains(userId)) {
            throw new RuntimeException("User has already voted for this movie");
        }

        movie.addVote(userId);
        saveMovie(movie);

        // Check if all cinema owners have voted
        long totalCinemaOwners = userRepository.countByRole("CINEMA_OWNER");
        long totalVotesForMovie = movie.getCinemaOwnerVotes().size();

        if (totalVotesForMovie >= totalCinemaOwners) {
            movie.setStatusMovie("END");
            movieRepository.save(movie);
        }   
    }
     public List<Movie> searchMovies(String keyword) {
        return movieRepository.findByTitleContainingIgnoreCase(keyword);
    }
    
    @Override
    public void updateAverageRating(Integer movieID) {
        List<Rating> ratings = ratingRepository.findByMovie_MovieID(movieID);
        double averageRating = ratings.stream().mapToInt(Rating::getScore).average().orElse(0.0);
        Movie movie = getMovieById(movieID);
        if (movie != null) {
            movie.setAverageRating(averageRating);
            movie.setRatingCount(ratings.size());
            movieRepository.save(movie);
        }
    }
    public boolean isDuplicateTitle(String translatedTitle) {
        List<Movie> movies = movieRepository.findAll();
        for (Movie movie : movies) {
            if (translatedTitle.equalsIgnoreCase(movie.getTitle())) {
                return true; // Trả về true nếu tìm thấy sự trùng lặp
            }
        }
        return false; // Trả về false nếu không tìm thấy sự trùng lặp
    }
    public boolean movieExistsByTitle(String title) {
        return movieRepository.existsByTitle(title);
    }
    @Override
    public List<Movie> getAllComingSoonMovies() {
        return movieRepository.findByStatusMovieAndAddressNotNull(Movie.StatusMovie.COMING_SOON.getStatus());
    }
    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre); 
    }
    public List<Movie> findByCinemaOwnerID(int theaterId) {
        return movieRepository.findByCinemaOwnerID(theaterId);
    }
    
}