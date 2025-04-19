package com.devtony.app.services;

import com.devtony.app.security.details.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuxiliarAuthService {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public CustomUserDetails getUserDetails() {
        Object principal = getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        }
        throw new IllegalStateException("El usuario autenticado no es válido");
    }

    //Métodos auxiliares
    public Long getId() {
        return getUserDetails().getId();
    }

    public String getEmail() {
        return getUserDetails().getEmail();
    }


    public String getName() {
        return getUserDetails().getName();
    }

    public Set<String> getRole() {
        return getUserDetails().getRoles();
    }
}
