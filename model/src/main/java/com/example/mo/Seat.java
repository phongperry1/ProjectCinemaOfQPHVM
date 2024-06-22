package com.example.mo;

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
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
   private String SeatID;
   private String SeatType;
   private boolean StatusSeat;
   private double Price;
=======
   private int SeatID;
   private String SeatType;
   private char StatusSeat;
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36


}