package com.devtony.app.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class EventInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String status; // Ej: "PENDING", "ACCEPTED", "REJECTED"
    private Instant invitationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    public EventInvitation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getInvitationDate() {
        return invitationDate;
    }

    public void setInvitationDate(Instant invitationDate) {
        this.invitationDate = invitationDate;
    }
}
