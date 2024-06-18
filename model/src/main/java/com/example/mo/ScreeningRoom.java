package com.example.mo;

import jakarta.persistence.Column;
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
public class ScreeningRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer ScreeningRoomID;

    @ManyToOne
    @JoinColumn(name = "theaterID", nullable = false)
    private Theater theater;

    @Column(length = 45, nullable = false, name = "roomname")
    private String Roomname;

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