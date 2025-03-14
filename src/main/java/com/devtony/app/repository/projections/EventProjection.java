package com.devtony.app.repository.projections;

import java.time.Instant;

public interface EventProjection {
    Long getId();
    String getName();
    Instant getDate();
    String getLocation();
    String getDescription();
    String getImage();
    Long getCreatorId();
    String getCreatorName();
}
