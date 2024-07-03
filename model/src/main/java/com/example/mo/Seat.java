package com.example.mo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private int seatId;

    @Column(name = "seat_name")
    private String seatName;

    @Column(name = "seat_type", nullable = false)
    private String seatType;

    @Column(name = "status_seat", nullable = false)
    private boolean statusSeat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "screening_room_id", nullable = false)
    private Integer screeningRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screening_room_id", insertable = false, updatable = false)
    private ScreeningRoom screeningRoom;

    @Column(name = "showtime_id", nullable = false)
    private Integer showtimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id", insertable = false, updatable = false)
    private Showtime showtime;
}
