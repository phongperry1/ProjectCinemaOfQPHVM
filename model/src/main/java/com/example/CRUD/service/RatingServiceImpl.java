package com.example.CRUD.service;

import com.example.CRUD.Repository.RatingRepository;
import com.example.mo.Movie;
import com.example.mo.Rating;
import com.example.mo.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final MovieService movieService;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, MovieService movieService) {
        this.ratingRepository = ratingRepository;
        this.movieService = movieService;
    }

    @Override
    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllRatingsByMovieId(Integer movieId) {
        return ratingRepository.findByMovie_MovieID(movieId);
    }

    

    @Override
    public void updateAverageRating(Integer movieID) {
        List<Rating> ratings = ratingRepository.findByMovie_MovieID(movieID);
        double averageRating = ratings.stream().mapToInt(Rating::getScore).average().orElse(0.0);
        Movie movie = movieService.getMovieById(movieID); // Use movieService to get movie by ID
        if (movie != null) {
            movie.setAverageRating(averageRating);
            movie.setRatingCount(ratings.size());
            movieService.saveMovie(movie);
        }
    }
     @Override
    public Rating getRatingByUserAndMovie(Users user, Integer movieId) {
        return ratingRepository.findByUserAndMovie_MovieID(user, movieId);
    }
    @Override
    public Rating getRatingById(Integer ratingId) {
        return ratingRepository.findById(ratingId).orElse(null);
    }

    @Override
    public void deleteRating(Integer ratingId) {
        ratingRepository.deleteById(ratingId);
    }
    
}