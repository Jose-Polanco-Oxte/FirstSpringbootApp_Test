package com.devtony.app.controllers;

import com.devtony.app.dto.InvitationEvent.CreateInvitationDto;
import com.devtony.app.dto.InvitationEvent.CreateInvitationsDto;
import com.devtony.app.dto.SimpleFormatBodyResponse;
import com.devtony.app.repository.projections.MyInvitationProjection;
import com.devtony.app.services.interfaces.IInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invitation")
public class InvitationController {

    @Autowired
    private IInvitationService invitationService;


    @GetMapping("/get-invitation-rejected")
    public ResponseEntity<List<MyInvitationProjection>> getInvitationRejected() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(invitationService.getUserInvitationsRejected());
    }

    @GetMapping("/get-invitation-pending")
    public ResponseEntity<List<MyInvitationProjection>> getInvitationPending() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(invitationService.getUserInvitationsPending());
    }

    @GetMapping("/get-invitation-accepted")
    public ResponseEntity<List<MyInvitationProjection>> getInvitationAccepted() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(invitationService.getUserInvitationsAccepted());
    }

    @PostMapping("/send-invitation")
    public ResponseEntity<SimpleFormatBodyResponse> sendInvitation(@RequestBody CreateInvitationDto createInvitationDto) {
        invitationService.createInvitation(createInvitationDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SimpleFormatBodyResponse("Invitación enviada correctamente"));
    }

    //Implementar posteriormente
    @PostMapping("/send-multiple-invitations")
    public ResponseEntity<SimpleFormatBodyResponse> sendManyInvitation(@RequestBody CreateInvitationsDto createInvitationsDto) {
        invitationService.createManyInvitations(createInvitationsDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SimpleFormatBodyResponse("Invitaciones enviadas correctamente"));
    }

    @GetMapping("/accept-invitation/{invitationId}")
    public ResponseEntity<SimpleFormatBodyResponse> acceptInvitation(@PathVariable Long invitationId) {
        invitationService.acceptInvitation(invitationId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SimpleFormatBodyResponse("Invitación aceptada correctamente"));
    }
}
