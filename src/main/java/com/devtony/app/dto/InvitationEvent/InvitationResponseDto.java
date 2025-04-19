package com.devtony.app.dto.InvitationEvent;

import com.devtony.app.model.Event;
import java.time.Instant;

public class InvitationResponseDto {

    static class EventInfo {
        protected Long id;
        protected String name;

        public EventInfo(Event event) {
            this.id = event.getId();
            this.name = event.getName();
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private Long id;
    private EventInfo info;
    private Instant invitationDate;
    private String status;

    public InvitationResponseDto(Long id, EventInfo info, Instant invitationDate, String status) {
        this.id = id;
        this.info = info;
        this.invitationDate = invitationDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventInfo getInfo() {
        return info;
    }

    public void setInfo(EventInfo info) {
        this.info = info;
    }

    public Instant getInvitationDate() {
        return invitationDate;
    }

    public void setInvitationDate(Instant invitationDate) {
        this.invitationDate = invitationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
