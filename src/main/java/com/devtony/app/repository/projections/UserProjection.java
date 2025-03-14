package com.devtony.app.repository.projections;

import java.util.Set;

public interface UserProjection {
    Long getId();
    String getName();
    String getEmail();
    Set<String> getRoles();
}
