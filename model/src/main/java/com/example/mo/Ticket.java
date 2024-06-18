package com.example.mo;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int TicketID;
    @ManyToOne // Assuming Ticket belongs to one Movie
    @JoinColumn(name = "movie_id") // Adjust the column name as per your database schema
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "showtimeID") // Tên cột khóa ngoại trong bảng Ticket
    private Showtime showtime;
    @ManyToOne // Assuming Ticket is associated with one Seat
    @JoinColumn(name = "seat_id") // Adjust the column name as per your database schema
    private Seat seat;
    private int FoodID;
    private Date ShowDate;
    @ManyToOne
    @JoinColumn(name = "theaterID") // Tên cột khóa ngoại trong bảng Ticket
    private Theater theater; // Thay vì int theaterID

    private double price;

}