package com.devtony.app.services;

import com.devtony.app.dto.InvitationEvent.AttendanceResponseDto;
import com.devtony.app.exception.AttendanceException;
import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.exception.InvitationException;
import com.devtony.app.model.Attendance;
import com.devtony.app.model.EventInvitation;
import com.devtony.app.repository.IAttendanceRepository;
import com.devtony.app.repository.IEventInvitationRepository;
import com.devtony.app.services.interfaces.IAttendanceService;
import com.devtony.app.services.validations.AttendanceValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AttendanceService implements IAttendanceService {

    private final IEventInvitationRepository eventInvitationRepository;
    private final IAttendanceRepository attendanceRepository;
    private final AuxiliarAuthService authService;
    private final AttendanceValidation attendanceValidation;

    public AttendanceService(IEventInvitationRepository eventInvitationRepository, IAttendanceRepository attendanceRepository, AuxiliarAuthService authService, AttendanceValidation attendanceValidation) {
        this.eventInvitationRepository = eventInvitationRepository;
        this.attendanceRepository = attendanceRepository;
        this.authService = authService;
        this.attendanceValidation = attendanceValidation;
    }

    @Transactional
    @Override
    public void markAttendance(Long eventId, String userEmail) throws AttendanceException {
        if (attendanceRepository.checkInOptimized(userEmail) == 0) {
            throw new AttendanceException("Could not checkIn",
                    new ExceptionDetails("No se pudo realizar el checkIn", "low"));
        }
    }

    @Override
    public AttendanceResponseDto getCheckInOut(Long eventId) {
        EventInvitation eventInvitation = eventInvitationRepository.findAcceptedInvitation(authService.getId(), eventId)
                .orElseThrow(() -> new AttendanceException("Invitation not found",
                        new ExceptionDetails("La invitación no fue aceptada o no existe", "low")));
        Attendance asistencia = attendanceRepository.getAttendance(eventInvitation.getId())
                .orElseThrow(() -> new AttendanceException("Attendance not found",
                new ExceptionDetails("No se encontró la asistencia", "low")));
        attendanceValidation.checkInOutReturn(asistencia.getCheckInTime());
        if (asistencia.getCheckOutTime() == null) {
            return new AttendanceResponseDto(eventInvitation.getEvent().getName(), String.valueOf(asistencia.getCheckInTime()),
                    "No se ha realizado el checkOut");
        }
        return new AttendanceResponseDto(eventInvitation.getEvent().getName(), String.valueOf(asistencia.getCheckInTime()),
                String.valueOf(asistencia.getCheckOutTime()));
    }

    public void checkOut(Long eventId, String userEmail) {
        if (attendanceRepository.checkOut(userEmail) == 0) {
            throw new AttendanceException("Could not checkOut",
                    new ExceptionDetails("No se pudo realizar el checkOut", "low"));
        }
    }

    @Override
    public String totalHoursInAllEvents() {
        List<Attendance> asistencia = attendanceRepository.getAttendanceByUserID(authService.getId())
                .filter(lista -> !lista.isEmpty())
                .orElseThrow(() -> new InvitationException("Attendance not found",
                new ExceptionDetails("No se encontró ninguna asistencia", "low")));
        //Cálculo de horas por stream
        Duration totalDuration = asistencia.stream()
                .map(attendance -> {
                    Instant checkIn = attendance.getCheckInTime();
                    Instant checkOut = attendance.getCheckOutTime();
                    if (checkOut == null || checkIn == null) {
                        return Duration.ZERO;
                    }
                    return Duration.between(checkIn, checkOut);
                })
                .reduce(Duration.ZERO, Duration::plus);
        return "%d horas, %d minutos, %s segundos".formatted(totalDuration.toHours(), totalDuration.toMinutesPart(), totalDuration.toSecondsPart());
    }
}
