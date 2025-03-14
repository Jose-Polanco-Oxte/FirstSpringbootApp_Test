package com.devtony.app.dto.InvitationEvent;

public class InvitationResponseDto {
    private Long id;
    private String event;
    private String invitationDate;
    private String status;

    public InvitationResponseDto(Long id, String event, String invitationDate, String status) {
        this.id = id;
        this.event = event;
        this.invitationDate = invitationDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getInvitationDate() {
        return invitationDate;
    }

    public void setInvitationDate(String invitationDate) {
        this.invitationDate = invitationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
