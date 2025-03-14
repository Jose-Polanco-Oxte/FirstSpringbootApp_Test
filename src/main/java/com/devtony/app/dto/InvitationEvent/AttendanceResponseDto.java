package com.devtony.app.dto.InvitationEvent;

public class AttendanceResponseDto {
    private String event;
    private String checkIn;
    private String checkOut;

    public AttendanceResponseDto(String event, String checkIn, String checkOut) {
        this.event = event;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }
}
