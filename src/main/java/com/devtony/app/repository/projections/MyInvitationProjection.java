package com.devtony.app.repository.projections;

public interface MyInvitationProjection {
    Long getId();
    EventInfo getEvent();
    String getStatus();
    interface EventInfo {
        Long getId();
        String getName();
    }
}