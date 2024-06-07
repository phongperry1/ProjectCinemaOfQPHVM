package com.example.mo;

import jakarta.persistence.Column;
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
public class ScreeningRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   
    private Integer ScreeningRoomID;

    @Column(name = "TheaterID", nullable = false)
    private Integer TheaterID;

   
    @Column(length = 45 ,nullable = false, name = "roomname")
    private String Roomname;

}