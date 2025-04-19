package com.devtony.app.security.details;

import com.devtony.app.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String qrCode;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Set<String> roles;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.roles = user.getRoles();
        this.authorities = roles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
        this.qrCode = user.getQrCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public Set<String> getRoles() {
        return this.roles;
    }

    public String getName() {
        return this.name;
    }

    public Long getId() {
        return this.id;
    }

    public String getQrCode() {
        return this.qrCode;
    }
}
