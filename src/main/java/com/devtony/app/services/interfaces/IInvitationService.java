package com.devtony.app.services.interfaces;

import com.devtony.app.dto.InvitationEvent.CreateInvitationDto;
import com.devtony.app.dto.InvitationEvent.CreateInvitationsDto;
import com.devtony.app.repository.projections.MyInvitationProjection;

import java.util.List;

public interface IInvitationService {
    public List<MyInvitationProjection> getUserInvitationsAccepted();
    public List<MyInvitationProjection> getUserInvitationsPending();
    public List<MyInvitationProjection> getUserInvitationsRejected();
    public void acceptInvitation(Long invitationId);
    public void rejectInvitation(Long invitationId);
    public void createInvitation(CreateInvitationDto createInvitationDto);
    public void deleteInvitation(Long invitationId);
    public void createManyInvitations(CreateInvitationsDto createInvitationsDto);
}