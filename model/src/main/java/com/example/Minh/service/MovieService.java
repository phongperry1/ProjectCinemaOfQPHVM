package com.example.Minh.service;

import com.example.Minh.model.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAllMovies();

    Movie getMovieById(Integer id);

    Movie saveMovie(Movie movie);

    void deleteMovie(Integer id);
}
