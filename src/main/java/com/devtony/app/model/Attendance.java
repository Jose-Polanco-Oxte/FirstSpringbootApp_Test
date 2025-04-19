package com.devtony.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "attended", columnDefinition = "BIT(1) default 0")
    private Boolean attended;
    private Instant checkInTime;  // Hora de entrada
    private Instant checkOutTime; // Hora de salida

    @ManyToOne
    @JoinColumn(name = "eventInvitation_id", nullable = false)
    private EventInvitation invitation; // Relación con la tabla invitation

    public Attendance() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventInvitation getInvitation() {
        return invitation;
    }

    public void setInvitation(EventInvitation invitation) {
        this.invitation = invitation;
    }

    public Boolean getAttended() {
        return attended;
    }

    public void setAttended(Boolean attended) {
        this.attended = attended;
    }

    public Instant getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Instant checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Instant getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Instant checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
}
