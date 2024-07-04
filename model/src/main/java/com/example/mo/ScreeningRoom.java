package com.example.mo;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    private Integer screeningRoomID;

    @ManyToOne
    @JoinColumn(name = "theater_id", nullable = false)    
    private Theater theater;

    @Column(length = 45, nullable = false, name = "roomname")
    private String roomname;

    @OneToMany(mappedBy = "screeningRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Showtime> showtimes = new ArrayList<>();

    public ScreeningRoom(Integer screeningRoomID, String roomName) {
        this.screeningRoomID = screeningRoomID;
        this.roomname = roomName;
    }

    public Integer getScreeningRoomID() {
        return this.screeningRoomID;
    }

    public void setScreeningRoomID(Integer screeningRoomID) {
        this.screeningRoomID = screeningRoomID;
    }

    public Integer getTheaterID() {
        return this.theater != null ? this.theater.getTheaterID() : null;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public String getRoomname() {
        return this.roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }
}
