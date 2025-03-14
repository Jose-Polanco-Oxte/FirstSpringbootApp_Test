package com.devtony.app.services.interfaces;

import com.devtony.app.dto.InvitationEvent.AttendanceResponseDto;


public interface IAttendanceService {
    public void markAttendance(Long eventID, String userEmail);
    public AttendanceResponseDto getCheckInOut(Long eventID);
    public String totalHoursInAllEvents();
    public void checkOut(Long eventID, String userEmail);
}