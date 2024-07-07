package com.example.CRUD.service;

import com.example.mo.Rating;
import com.example.mo.Users;

import java.util.List;

public interface RatingService {
    Rating saveRating(Rating rating);
    List<Rating> getAllRatingsByMovieId(Integer movieId);
    Rating getRatingByUserAndMovie(Users user, Integer movieId);
    void updateAverageRating(Integer movieID);
    Rating getRatingById(Integer ratingId);
    void deleteRating(Integer ratingId);
}