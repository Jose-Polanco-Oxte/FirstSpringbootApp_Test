package com.devtony.app.services;

import com.devtony.app.dto.InvitationEvent.CreateInvitationDto;
import com.devtony.app.dto.InvitationEvent.CreateInvitationsDto;
import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.exception.InvitationException;
import com.devtony.app.model.Attendance;
import com.devtony.app.model.EventInvitation;
import com.devtony.app.repository.IAttendanceRepository;
import com.devtony.app.repository.IEventInvitationRepository;
import com.devtony.app.repository.IEventRepository;
import com.devtony.app.repository.IUserRepository;
import com.devtony.app.repository.projections.MyInvitationProjection;
import com.devtony.app.services.interfaces.IInvitationService;
import com.devtony.app.services.validations.InvitationValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class InvitationService implements IInvitationService {

    private final IEventRepository eventRepository;
    private final IUserRepository userRepository;
    private final IAttendanceRepository attendanceRepository;
    private final IEventInvitationRepository eventInvitationRepository;
    private final AuxiliarAuthService authService;
    private final InvitationValidation invitationValidation;

    public InvitationService(IEventRepository eventRepository, IUserRepository userRepository,
                             IAttendanceRepository attendanceRepository, IEventInvitationRepository eventInvitationRepository,
                             AuxiliarAuthService authService, InvitationValidation invitationValidation) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
        this.eventInvitationRepository = eventInvitationRepository;
        this.authService = authService;
        this.invitationValidation = invitationValidation;
    }

    @Override
    public List<MyInvitationProjection> getUserInvitationsAccepted() throws InvitationException {
        List<MyInvitationProjection> invitations = eventInvitationRepository.findAllAcceptedInvitationsByUser(authService.getId());
        invitationValidation.validateInvitationListReturn(invitations);
        return invitations;
    }

    @Override
    public List<MyInvitationProjection> getUserInvitationsPending() throws InvitationException {
        List<MyInvitationProjection> invitations = eventInvitationRepository.findAllPendingInvitationsByUser(authService.getId());
        invitationValidation.validateInvitationListReturn(invitations);
        return invitations;
    }

    @Override
    public List<MyInvitationProjection> getUserInvitationsRejected() throws InvitationException {
        List<MyInvitationProjection> invitations = eventInvitationRepository.findAllRejectedInvitationsByUser(authService.getId());
        invitationValidation.validateInvitationListReturn(invitations);
        return invitations;
    }

    @Transactional
    @Override
    public void acceptInvitation(Long invitationId) throws InvitationException {
        EventInvitation invitation = eventInvitationRepository.findById(invitationId)
                .orElseThrow(() -> new InvitationException("Invitation not found",
                        new ExceptionDetails("La invitación no existe", "low")));
        invitationValidation.validateInvitation(invitation, authService.getId(), InvitationValidation.Status.ACCEPT);
        invitation.setStatus("ACCEPTED");
        eventInvitationRepository.save(invitation);
        Attendance attendance = new Attendance();
        attendance.setInvitation(invitation);
        attendanceRepository.save(attendance);
    }

    @Transactional
    @Override
    public void rejectInvitation(Long invitationId) throws InvitationException {
        EventInvitation invitation = eventInvitationRepository.findById(invitationId)
                .orElseThrow(() -> new InvitationException("Invitation not found",
                        new ExceptionDetails("La invitación no existe", "low")));
        invitationValidation.validateInvitation(invitation, authService.getId(), InvitationValidation.Status.REJECT);
        invitation.setStatus("REJECTED");
        eventInvitationRepository.save(invitation);
    }

    @Transactional
    @Override
    public void createInvitation(CreateInvitationDto createInvitationDto) throws InvitationException {
        eventInvitationRepository.findMySentInvitationByEventId(createInvitationDto.getEventId(), authService.getId())
                .ifPresent(invitation -> {
                    throw new InvitationException("You have already sent an invitation to this user",
                            new ExceptionDetails("Ya has enviado una invitación a este usuario", "low"));
                });

        EventInvitation invitation = new EventInvitation();
        invitation.setUser(userRepository.findById(createInvitationDto.getReceivingUserId())
                .orElseThrow(() -> new InvitationException("User not found",
                        new ExceptionDetails("El usuario no existe", "low"))));

        invitation.setEvent(eventRepository.findById(createInvitationDto.getEventId())
                .orElseThrow(() -> new InvitationException("Event not found",
                        new ExceptionDetails("El evento no existe", "low"))));
        invitation.setInvitationDate(Instant.now());
        invitation.setStatus("PENDING");
        eventInvitationRepository.save(invitation);
    }

    @Transactional
    @Override
    public void deleteInvitation(Long invitationId) throws InvitationException {
        EventInvitation invitation = eventInvitationRepository.findById(invitationId)
                .orElseThrow(() -> new InvitationException("Invitation not found",
                        new ExceptionDetails("La invitación no existe", "low")));
        eventInvitationRepository.delete(invitation);
    }

    @Transactional
    @Override
    public void createManyInvitations(CreateInvitationsDto createInvitationsDto) throws InvitationException {
        List<Long> ids = createInvitationsDto.getReceivingUserIds();
        Long eventId = createInvitationsDto.getEventId();
        for (Long id : ids) {
            try {
                createInvitation(new CreateInvitationDto(id, eventId));
            } catch (InvitationException e) {
                if (e.getMessage().equals("Event not found")) {
                    throw e;
                }
            }
        }
    }
}
