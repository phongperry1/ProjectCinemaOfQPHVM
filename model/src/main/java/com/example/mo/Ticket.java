package com.example.mo;

import java.sql.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
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
    private int ticketID;

    private int showtimeID;
    private int seatID;
    private int foodID;
    private Date showDate;
    private int theaterID;
    private double price;

    private Integer movieID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movieID", insertable = false, updatable = false)
    private Movie movie;
}
