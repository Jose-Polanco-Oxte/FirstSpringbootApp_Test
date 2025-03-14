package com.devtony.app.dto.admin;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserAdminRequestDto{
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private List<String> roles;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getRoles() {
        return new HashSet<>(roles);
    }
}
