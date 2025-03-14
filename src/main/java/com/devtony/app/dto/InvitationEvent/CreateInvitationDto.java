package com.devtony.app.dto.InvitationEvent;

public class CreateInvitationDto {
    private Long eventId;
    private Long receivingUserId;

    public CreateInvitationDto(Long receivingUserId, Long eventId) {
        this.receivingUserId = receivingUserId;
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getReceivingUserId() {
        return receivingUserId;
    }

    public void setReceivingUserId(Long receivingUserId) {
        this.receivingUserId = receivingUserId;
    }
}
