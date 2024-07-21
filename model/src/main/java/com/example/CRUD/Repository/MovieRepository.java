package com.example.CRUD.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mo.Movie;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByTitleContainingIgnoreCase(String keyword);
    boolean existsByTitle(String title);
    List<Movie> findByStatusMovieAndAddressNotNull(String statusMovie);
    List<Movie> findByGenre(String genre);
    List<Movie> findByCinemaOwnerID(Integer cinemaOwnerID);
}