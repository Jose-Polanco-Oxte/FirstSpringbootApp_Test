package com.devtony.app.services;

import com.devtony.app.dto.InvitationEvent.AttendanceResponseDto;
import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.exception.InvitationException;
import com.devtony.app.model.Attendance;
import com.devtony.app.model.Event;
import com.devtony.app.model.EventInvitation;
import com.devtony.app.repository.IAttendanceRepository;
import com.devtony.app.repository.IEventInvitationRepository;
import com.devtony.app.repository.IEventRepository;
import com.devtony.app.services.interfaces.IAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AttendanceService implements IAttendanceService {

    @Autowired
    private IEventInvitationRepository eventInvitationRepository;

    @Autowired
    private IEventRepository eventRepository;

    @Autowired
    private IAttendanceRepository attendanceRepository;

    @Override
    public void markAttendance(Long eventID, String userEmail) {
        /*Verificar que el evento ya haya terminado*/
        AuxiliarAuthService authService = new AuxiliarAuthService();
        attendanceRepository.findAcceptedInvitation(eventID, userEmail)
                .orElseThrow(() -> new InvitationException("Invitation not found",
                        new ExceptionDetails("La invitación no fue aceptada o no existe", "low")));
        if (attendanceRepository.checkIn(userEmail) == 0) {
            throw new InvitationException("CheckIn not found",
                    new ExceptionDetails("No se pudo realizar el checkIn", "low"));
        }
    }

    @Override
    public AttendanceResponseDto getCheckInOut(Long eventID) {
        AuxiliarAuthService authService = new AuxiliarAuthService();
        Event evento = eventRepository.findById(eventID)
                .orElseThrow(() -> new InvitationException("Event not found",
                new ExceptionDetails("El evento no existe", "low")));
        EventInvitation eventInvitation = evento.getInvitations().stream()
                .filter(invitation -> invitation.getUser().getId().equals(authService.getId()))
                .filter(invitation -> invitation.getStatus().equals("ACCEPTED"))
                .findFirst()
                .orElseThrow(() -> new InvitationException("Invitation not found",
                        new ExceptionDetails("La invitación no fue aceptada o no existe", "low")));
        Attendance asistencia = attendanceRepository.getAttendance(eventInvitation.getId())
                .orElseThrow(() -> new InvitationException("Attendance not found",
                new ExceptionDetails("No se encontró la asistencia", "low")));
        if (asistencia.getCheckInTime() == null) {
            throw new InvitationException("CheckIn not found",
                    new ExceptionDetails("No se ha realizado el checkIn", "low"));
        }
        if (asistencia.getCheckOutTime() == null) {
            return new AttendanceResponseDto(evento.getName(), String.valueOf(asistencia.getCheckInTime()), "No se ha realizado el checkOut");
        }

        return new AttendanceResponseDto(evento.getName(), String.valueOf(asistencia.getCheckInTime()), String.valueOf(asistencia.getCheckOutTime()));
    }

    public void checkOut(Long eventID, String userEmail) {
        AuxiliarAuthService authService = new AuxiliarAuthService();
        attendanceRepository.findAcceptedInvitation(eventID, userEmail)
                .orElseThrow(() -> new InvitationException("Invitation not found",
                        new ExceptionDetails("La invitación no fue aceptada o no existe", "low")));
        Event evento = eventRepository.findById(eventID)
                .orElseThrow(() -> new InvitationException("Event not found",
                new ExceptionDetails("El evento no existe", "low")));
        EventInvitation eventInvitation = evento.getInvitations().stream()
                .filter(invitation -> invitation.getUser().getId().equals(authService.getId()))
                .filter(invitation -> invitation.getStatus().equals("ACCEPTED"))
                .findFirst()
                .orElseThrow(() -> new InvitationException("Invitation not found",
                        new ExceptionDetails("La invitación no fue aceptada o no existe", "low")));
        Attendance asistencia = attendanceRepository.getAttendance(eventInvitation.getId())
                .orElseThrow(() -> new InvitationException("Attendance not found",
                new ExceptionDetails("No se encontró la asistencia", "low")));
        if (asistencia.getCheckInTime() == null) {
            throw new InvitationException("CheckIn not found",
                    new ExceptionDetails("No se ha realizado el checkIn", "low"));
        }
        if (asistencia.getCheckOutTime() != null) {
            throw new InvitationException("CheckOut not found",
                    new ExceptionDetails("Ya se realizó el checkOut", "low"));
        }
        if (attendanceRepository.checkOut(userEmail) == 0) {
            throw new InvitationException("CheckOut not found",
                    new ExceptionDetails("No se pudo realizar el checkOut", "low"));
        }
    }

    /* Logica fuerte:V */
    @Override
    public String totalHoursInAllEvents() {
        AuxiliarAuthService authService = new AuxiliarAuthService();

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
