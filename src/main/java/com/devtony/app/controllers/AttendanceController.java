package com.devtony.app.controllers;

import com.devtony.app.dto.InvitationEvent.AttendanceResponseDto;
import com.devtony.app.dto.SimpleFormatBodyResponse;
import com.devtony.app.services.interfaces.IAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private IAttendanceService attendanceService;

    @GetMapping("/get-totalHours")
    public ResponseEntity<String> getTotalHours() {
        return ResponseEntity.ok(attendanceService.totalHoursInAllEvents());
    }

    @PostMapping("/mark-attendance/{eventID}/{userEmail}")
    public ResponseEntity<SimpleFormatBodyResponse> markAttendance(@PathVariable Long eventID, @PathVariable String userEmail) {
        attendanceService.markAttendance(eventID, userEmail);
        return ResponseEntity.ok(new SimpleFormatBodyResponse("Asistencia marcada correctamente"));
    }

    @PostMapping("/check-out/{eventID}/{userEmail}")
    public ResponseEntity<SimpleFormatBodyResponse> checkOut(@PathVariable Long eventID, @PathVariable String userEmail) {
        attendanceService.checkOut(eventID, userEmail);
        return ResponseEntity.ok(new SimpleFormatBodyResponse("CheckOut realizado correctamente"));
    }

    @GetMapping("/get-checkInOut/{eventID}")
    public ResponseEntity<AttendanceResponseDto> getCheckInOut(@PathVariable Long eventID) {
        return ResponseEntity.ok(attendanceService.getCheckInOut(eventID));
    }
}
