package com.example.mo;

import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
   private int ShowtimeID;
   private int MovieID;
   private int ScreeningRoomID;
   private Date ShowDate;
   private Time ShowTime;
}