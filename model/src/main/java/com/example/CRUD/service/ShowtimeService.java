package com.example.CRUD.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.CRUD.Repository.ScreeningRoomRepository;
import com.example.CRUD.Repository.ShowtimeRepository;
import com.example.CRUD.controller.ShowtimeNotFoundException;
import com.example.mo.ScreeningRoom;
import com.example.mo.Showtime;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ShowtimeService {

    @Autowired
    private ShowtimeRepository repo;

    @Autowired
    private ScreeningRoomRepository screeningRoomRepository;

    public Date getShowDate(Integer showtimeId) {
        return repo.findShowDateByShowtimeId(showtimeId);
    }

    public Time getShowtimeNameById(int showtimeId) {
        Showtime showtime = repo.findById(showtimeId)
                .orElseThrow(() -> new EntityNotFoundException("Showtime not found"));
        return showtime.getShowTime();
    }

    public List<Showtime> getShowtimesByMovieID(Integer movieID) {
        List<Showtime> showtimes = repo.findByMovie_MovieID(movieID);
        System.out.println("Fetched showtimes for movie ID " + movieID + ": " + showtimes);
        return showtimes;
    }

    public List<Object[]> getShowtimesByMovieIDAndTheaterID(Integer movieID, Integer theaterID) {
        return repo.findByMovieIDAndTheaterID(movieID, theaterID);
    }

    public Page<Showtime> getShowtimesByMovieIDAndCinemaOwnerID(Integer movieID, Integer cinemaOwnerID, Pageable pageable) {
        return repo.findByMovieIDAndCinemaOwnerID(movieID, cinemaOwnerID, pageable);
    }

    public Page<Showtime> getShowtimesByDate(Date showDate, Pageable pageable) {
        return repo.findByShowDate(showDate, pageable);
    }

    public Page<Showtime> listAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<Showtime> listAllByCinemaOwnerID(Integer cinemaOwnerID, Pageable pageable) {
        return repo.findByCinemaOwnerID(cinemaOwnerID, pageable);
    }

    public void save(Showtime showtime) {
        repo.save(showtime);
    }

    public Integer getTheaterIdByScreeningRoomId(Integer screeningRoomId) {
        Optional<ScreeningRoom> screeningRoomOptional = screeningRoomRepository.findById(screeningRoomId);
        if (screeningRoomOptional.isPresent()) {
            return screeningRoomOptional.get().getTheater().getTheaterID();
        }
        return null;
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
