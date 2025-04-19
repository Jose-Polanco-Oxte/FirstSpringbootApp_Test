package com.devtony.app.repository.projections;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public interface EventProjection {
    Long getId();
    String getName();
    Instant getDate();
    String getDescription();
    String getLocation();
    String getImage();
    String getCreatorName();
    Long getCreatorId();
}
