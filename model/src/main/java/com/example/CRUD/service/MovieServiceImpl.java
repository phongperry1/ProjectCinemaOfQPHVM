package com.example.CRUD.service;

import com.example.CRUD.Repository.MovieRepository;
import com.example.mo.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> getAllMoviesByCinemaOwnerID(Integer cinemaOwnerID) {
        return movieRepository.findByCinemaOwnerID(cinemaOwnerID);
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
}
