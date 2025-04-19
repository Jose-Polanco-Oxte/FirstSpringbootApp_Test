package com.devtony.app.services.interfaces;

import com.devtony.app.dto.InvitationEvent.AttendanceResponseDto;


public interface IAttendanceService {
    public void markAttendance(Long eventId, String userEmail);
    public AttendanceResponseDto getCheckInOut(Long eventId);
    public String totalHoursInAllEvents();
    public void checkOut(Long eventId, String userEmail);
}