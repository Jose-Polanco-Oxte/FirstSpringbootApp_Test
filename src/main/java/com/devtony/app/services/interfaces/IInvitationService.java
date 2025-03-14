package com.devtony.app.services.interfaces;

import com.devtony.app.dto.InvitationEvent.CreateInvitationDto;
import com.devtony.app.repository.projections.InvitationProjection;

import java.util.List;

public interface IInvitationService {
    public List<InvitationProjection> getUserInvitationsAccepted();
    public List<InvitationProjection> getUserInvitationsPending();
    public List<InvitationProjection> getUserInvitationsRejected();
    public void acceptInvitation(Long invitationId);
    public void rejectInvitation(Long invitationId);
    public void createInvitation(CreateInvitationDto createInvitationDto);
    public void deleteInvitation(Long invitationId);
}