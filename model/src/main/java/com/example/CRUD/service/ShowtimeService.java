package com.example.CRUD.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.ShowtimeRepository;
import com.example.CRUD.controller.ShowtimeNotFoundException;
import com.example.mo.Showtime;

@Service
public class ShowtimeService {

    @Autowired
    private ShowtimeRepository repo;

    public List<Showtime> getShowtimesByMovieID(Integer movieID) {
        return repo.findByMovieID(movieID);
    }

    public List<Showtime> getShowtimesByMovieIDAndCinemaOwnerID(Integer movieID, Integer cinemaOwnerID) {
        return repo.findByMovieIDAndCinemaOwnerID(movieID, cinemaOwnerID);
    }

    public List<Showtime> getShowtimesByDate(Date showDate) {
        return repo.findByShowDate(showDate);
    }

    public List<Showtime> listAll() {
        return repo.findAll();
    }

    public List<Showtime> listAllByCinemaOwnerID(Integer cinemaOwnerID) {
        return repo.findByCinemaOwnerID(cinemaOwnerID);
    }

    public void save(Showtime showtime) {
        repo.save(showtime);
    }

    public Showtime get(Integer showtimeID) throws ShowtimeNotFoundException {
        Optional<Showtime> result = repo.findById(showtimeID);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ShowtimeNotFoundException("Could not find any showtime with ID " + showtimeID);
    }

    public void delete(Integer showtimeID) throws ShowtimeNotFoundException {
        if (!repo.existsById(showtimeID)) {
            throw new ShowtimeNotFoundException("Could not find any showtime with ID " + showtimeID);
        }
        repo.deleteById(showtimeID);
    }
}
