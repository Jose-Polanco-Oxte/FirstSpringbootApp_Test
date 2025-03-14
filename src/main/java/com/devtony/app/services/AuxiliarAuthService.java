package com.devtony.app.services;

import com.devtony.app.security.details.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

public class AuxiliarAuthService {
    private Authentication authentication;
    private CustomUserDetails userDetails;

    public AuxiliarAuthService() {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        this.userDetails = (CustomUserDetails) authentication.getPrincipal();
    }

    //Getters
    public Authentication getAuthentication() {
        return authentication;
    }

    public CustomUserDetails getUserDetails() {
        return userDetails;
    }

    //MEtodos auxiliares
    public Long getId() {
        return userDetails.getId();
    }

    public String getEmail() {
        return userDetails.getEmail();
    }


    public String getName() {
        return userDetails.getName();
    }

    public Set<String> getRole() {
        return userDetails.getRoles();
    }
}
