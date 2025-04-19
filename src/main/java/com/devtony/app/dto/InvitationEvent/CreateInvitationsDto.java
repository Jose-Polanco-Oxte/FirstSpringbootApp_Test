package com.devtony.app.dto.InvitationEvent;

import java.util.List;

/**
 * Project: app
 * Created by: anton
 * Date: 31/03/2025
 * Time: 11:03 a. m.
 */
public class CreateInvitationsDto {
    private Long eventId;
    private List<Long> receivingUserIds;

    public CreateInvitationsDto(Long eventId, List<Long> receivingUserIds) {
        this.eventId = eventId;
        this.receivingUserIds = receivingUserIds;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public List<Long> getReceivingUserIds() {
        return receivingUserIds;
    }

    public void setReceivingUserIds(List<Long> receivingUserIds) {
        this.receivingUserIds = receivingUserIds;
    }
}
