package com.devtony.app.security.auth;

import com.devtony.app.security.details.CustomUserDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final CustomUserDetails principal;
    private String credentials;

    // Constructor para usuarios autenticados
    public JwtAuthenticationToken(CustomUserDetails principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true); // Indica que esta autenticación ya está verificada
    }

    // Constructor para usuarios no autenticados (si fuera necesario)
    public JwtAuthenticationToken(String credentials) {
        super(null);
        this.principal = null;
        this.credentials = credentials;
        super.setAuthenticated(false); // Indica que esta autenticación aún no ha sido verificada
    }

    @Override
    public Object getCredentials() {
        return credentials; // El token JWT
    }

    @Override
    public Object getPrincipal() {
        return principal; // El UserDetails del usuario
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("No puedes establecer manualmente este token como autenticado.");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null; // Borra las credenciales (JWT) si es necesario
    }
}

