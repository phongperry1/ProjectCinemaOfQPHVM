package com.example.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mo.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    
}
