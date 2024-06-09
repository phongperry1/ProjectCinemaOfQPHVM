package com.example.CRUD.service;

import java.util.List;

import com.example.mo.Movie;

public interface MovieService {

    List<Movie> getAllMovies();

    Movie getMovieById(Integer id);

    Movie saveMovie(Movie movie);

    void deleteMovie(Integer id);
}
