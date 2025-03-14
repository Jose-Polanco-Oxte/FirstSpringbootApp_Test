package com.devtony.app.services;

import com.devtony.app.dto.InvitationEvent.CreateInvitationDto;
import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.exception.InvitationException;
import com.devtony.app.model.Attendance;
import com.devtony.app.model.EventInvitation;
import com.devtony.app.repository.IAttendanceRepository;
import com.devtony.app.repository.IEventInvitationRepository;
import com.devtony.app.repository.IEventRepository;
import com.devtony.app.repository.IUserRepository;
import com.devtony.app.repository.projections.InvitationProjection;
import com.devtony.app.services.interfaces.IInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class InvitationService implements IInvitationService {

    @Autowired
    private IEventRepository eventRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAttendanceRepository attendanceRepository;

    @Autowired
    private IEventInvitationRepository eventInvitationRepository;

    @Override
    public List<InvitationProjection> getUserInvitationsAccepted() {
        AuxiliarAuthService authService = new AuxiliarAuthService();
        if (eventInvitationRepository.count() == 0) {
            throw new InvitationException("No invitations found",
                    new ExceptionDetails("No existen invitaciones actualmente", "low"));
        }
        return eventInvitationRepository.findAllAcceptedInvitationsByUser(authService.getId())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new InvitationException("No accepted invitations found",
                        new ExceptionDetails("Ninguna invitación ha sido aceptada aún", "low")));
    }

    @Override
    public List<InvitationProjection> getUserInvitationsPending() {
        AuxiliarAuthService authService = new AuxiliarAuthService();
        if (eventInvitationRepository.count() == 0) {
            throw new InvitationException("No invitations found",
                    new ExceptionDetails("No existen invitaciones actualmente", "low"));
        }
        return eventInvitationRepository.findAllPendingInvitationsByUser(authService.getId())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new InvitationException("No pending invitations found",
                        new ExceptionDetails("No hay invitaciones pendientes", "low")));
    }

    @Override
    public List<InvitationProjection> getUserInvitationsRejected() {
        AuxiliarAuthService authService = new AuxiliarAuthService();
        if (eventInvitationRepository.count() == 0) {
            throw new InvitationException("No invitations found",
                    new ExceptionDetails("No existen invitaciones actualmente", "low"));
        }
        return eventInvitationRepository.findAllRejectedInvitationsByUser(authService.getId())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new InvitationException("No rejected invitations found",
                        new ExceptionDetails("No hay invitaciones rechazadas", "low")));
    }

    @Override
    public void acceptInvitation(Long invitationId) {
        AuxiliarAuthService authService = new AuxiliarAuthService();

        EventInvitation invitation = eventInvitationRepository.findById(invitationId)
                .orElseThrow(() -> new InvitationException("Invitation not found",
                        new ExceptionDetails("La invitación no existe", "low")));
        if (!invitation.getUser().getId().equals(authService.getId())) {
            throw new InvitationException("The invitation does not correspond to the user",
                    new ExceptionDetails("Se intenta aceptar una invitación que no es suya", "medium"));
        }
        if (invitation.getStatus().equals("ACCEPTED")) {
            throw new InvitationException("The invitation has been accepted",
                    new ExceptionDetails("La invitación ya ha sido aceptada", "low"));
        }
        invitation.setStatus("ACCEPTED");
        eventInvitationRepository.save(invitation);
        Attendance attendance = new Attendance();
        attendance.setInvitation(invitation);
        attendanceRepository.save(attendance);
    }

    @Override
    public void rejectInvitation(Long invitationId) {
        AuxiliarAuthService authService = new AuxiliarAuthService();

        EventInvitation invitation = eventInvitationRepository.findById(invitationId)
                .orElseThrow(() -> new InvitationException("Invitation not found",
                        new ExceptionDetails("La invitación no existe", "low")));
        if (invitation.getStatus().equals("REJECTED")) {
            throw new InvitationException("The invitation has been rejected",
                    new ExceptionDetails("La invitación ya ha sido rechazada", "low"));
        }
        if (!invitation.getUser().getId().equals(authService.getId())) {
            throw new InvitationException("The invitation does not correspond to the user",
                    new ExceptionDetails("Se intenta aceptar una invitación que no es suya", "medium"));
        }
        invitation.setStatus("REJECTED");
        eventInvitationRepository.save(invitation);
    }

    @Override
    public void createInvitation(CreateInvitationDto createInvitationDto) {
        AuxiliarAuthService authService = new AuxiliarAuthService();
        if (eventInvitationRepository
                .existsByEvent_IdAndUser_Id(createInvitationDto.getEventId(), createInvitationDto.getReceivingUserId())) {
            throw new InvitationException("Invitation already exists",
                    new ExceptionDetails("La invitación ya existe", "low"));
        }

        EventInvitation invitation = new EventInvitation();
        invitation.setUser(userRepository.findById(createInvitationDto.getReceivingUserId())
                .orElseThrow(() -> new InvitationException("User not found",
                        new ExceptionDetails("El usuario no existe", "low"))));

        invitation.setEvent(eventRepository.findById(createInvitationDto.getEventId())
                .orElseThrow(() -> new InvitationException("Event not found",
                        new ExceptionDetails("El evento no existe", "low"))));

        invitation.setStatus("PENDING");
        invitation.setInvitationDate(Instant.now());
        eventInvitationRepository.save(invitation);
    }

    @Override
    public void deleteInvitation(Long invitationId) {
        EventInvitation invitation = eventInvitationRepository.findById(invitationId)
                .orElseThrow(() -> new InvitationException("Invitation not found",
                        new ExceptionDetails("La invitación no existe", "low")));
        eventInvitationRepository.delete(invitation);
    }
}
