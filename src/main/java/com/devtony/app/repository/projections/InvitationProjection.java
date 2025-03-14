package com.devtony.app.repository.projections;

public interface InvitationProjection {
    Long getId();
    String getStatus();
    EventInfo getEvent();
    interface EventInfo {
        Long getId();
        String getName();
    }
}