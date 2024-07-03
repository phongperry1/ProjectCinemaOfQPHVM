package com.example.mo;

import java.sql.Date;
import java.sql.Time;

public class ShowtimeDTO {
    private int showtimeId;
    private Date showDate;
    private Time showTime;

    public ShowtimeDTO(Integer showtimeId, Date showDate, Time showTime) {
        this.showtimeId = showtimeId;
        this.showDate = showDate;
        this.showTime = showTime;
    }

    // Getters and setters

    public int getShowtimeId() {
        return this.showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public Time getShowTime() {
        return showTime;
    }

    public void setShowTime(Time showTime) {
        this.showTime = showTime;
    }
}