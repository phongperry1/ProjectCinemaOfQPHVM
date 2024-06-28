package com.example.mo;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Showtime {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer ShowtimeID;
   
    @ManyToOne
    @JoinColumn(name = "movieID", nullable = false)
    private Movie movie;

    private Integer ScreeningRoomID;

    @Column(name = "showDate")
    private Date showDate;

    @Column(name = "showTime")
    private Time showTime;

    @ManyToOne
    @JoinColumn(name = "theaterID", nullable = false)
    private Theater theater;
}