package com.example.mo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
public class ScreeningRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screening_room_id", nullable = false)
    private Integer ScreeningRoomID;

    @ManyToOne
    @JoinColumn(name = "theater_id")    
    private Theater theater;

   
    @Column(name = "roomname")
    private String Roomname;

    @OneToMany(mappedBy = "screeningRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Showtime> showtimes = new ArrayList<>();

    public ScreeningRoom(Integer screeningRoomID, String roomName) {
        this.ScreeningRoomID = screeningRoomID;
        this.Roomname = roomName;
    }


    public Integer getScreeningRoomID() {
        return this.ScreeningRoomID;
    }

    public void setScreeningRoomID(Integer ScreeningRoomID) {
        this.ScreeningRoomID = ScreeningRoomID;
    }


    public String getRoomname() {
        return this.Roomname;
    }

    public void setRoomname(String Roomname) {
        this.Roomname = Roomname;
    }


    


   


}