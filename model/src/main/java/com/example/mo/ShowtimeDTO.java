package com.example.mo;

import java.sql.Date;
import java.sql.Time;

public class ShowtimeDTO {
    private Date showDate;
    private Time showTime;

    public ShowtimeDTO(Date showDate, Time showTime) {
        this.showDate = showDate;
        this.showTime = showTime;
    }

    // Getters and setters
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
