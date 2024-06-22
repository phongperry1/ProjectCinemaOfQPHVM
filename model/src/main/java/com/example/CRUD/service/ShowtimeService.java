package com.example.CRUD.service;

<<<<<<< HEAD
import java.sql.Date;

=======
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
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

<<<<<<< HEAD
    public List<Object[]> getShowtimesByMovieIDAndTheaterID(Integer movieID, Integer theaterID) {
        return repo.findByMovieIDAndTheaterID(movieID, theaterID);
    }

    public List<Showtime> getShowtimesByDate(Date ShowDate) {
        return repo.findByShowDate(ShowDate);
    }

=======
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
    public List<Showtime> listAll() {
        return (List<Showtime>) repo.findAll();
    }

     public void save(Showtime showtime) {
        repo.save(showtime);
    }

    public Showtime get(Integer showtimeID) throws ShowtimeNotFoundException {
        Optional<Showtime> result = repo.findById(showtimeID);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ShowtimeNotFoundException("Could not find any theater with ID " + showtimeID);
    }

    public void delete(Integer ShowtimeID) throws ShowtimeNotFoundException {
        if (!repo.existsById(ShowtimeID)) {
            throw new ShowtimeNotFoundException("Could not find any theater with ID " + ShowtimeID);
        }
        repo.deleteById(ShowtimeID);
    } 



}