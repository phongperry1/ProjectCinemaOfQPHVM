package com.example.mo;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TicketDTO {
    private String userId;
    private String movieId;
    private String showtimeId;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date showdate;
    private List<Seat> selectedSeats;
    private List<String> selectedFood;
    private double totalPrice3;
    private String theaterId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(String theaterId) {
        this.theaterId = theaterId;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public Date getShowdate() {
        return showdate;
    }

    public void setShowdate(Date showdate) {
        this.showdate = showdate;
    }

    public List<Seat> getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(List<Seat> selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    public List<String> getSelectedFood() {
        return selectedFood;
    }

    public void setSelectedFood(List<String> selectedFood) {
        this.selectedFood = selectedFood;
    }

    public double getTotalPrice3() {
        return totalPrice3;
    }

    public void setTotalPrice3(double totalPrice3) {
        this.totalPrice3 = totalPrice3;
    }
}