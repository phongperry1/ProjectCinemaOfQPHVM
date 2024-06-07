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
import jakarta.persistence.Column;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Showtime {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

   private Integer ShowtimeID;
   private Integer MovieID;
   private Integer ScreeningRoomID;

   @Column(name = "ShowDate")
   private Date ShowDate;
   @Column(name = "ShowTime" )
   private Time ShowTime;


   public Integer getShowtimeID() {
      return this.ShowtimeID;
   }

   public void setShowtimeID(Integer ShowtimeID) {
      this.ShowtimeID = ShowtimeID;
   }

   public Integer getMovieID() {
      return this.MovieID;
   }

   public void setMovieID(Integer MovieID) {
      this.MovieID = MovieID;
   }

   public Integer getScreeningRoomID() {
      return this.ScreeningRoomID;
   }

   public void setScreeningRoomID(Integer ScreeningRoomID) {
      this.ScreeningRoomID = ScreeningRoomID;
   }

   public Date getShowDate() {
      return this.ShowDate;
   }

   public void setShowDate(Date ShowDate) {
      this.ShowDate = ShowDate;
   }

   public Time getShowTime() {
      return this.ShowTime;
   }

   public void setShowTime(Time ShowTime) {
      this.ShowTime = ShowTime;
   }

}