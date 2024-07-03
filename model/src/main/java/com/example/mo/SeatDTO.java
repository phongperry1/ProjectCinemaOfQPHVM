package com.example.mo;

public class SeatDTO {
    private String seatName;
    private String seatType;
    private boolean statusSeat;

    public SeatDTO(String seatName, String seatType, boolean statusSeat) {
        this.seatName = seatName;
        this.seatType = seatType;
        this.statusSeat = statusSeat;
    }

    // Getters and setters
    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public boolean isStatusSeat() {
        return statusSeat;
    }

    public void setStatusSeat(boolean statusSeat) {
        this.statusSeat = statusSeat;
    }
}