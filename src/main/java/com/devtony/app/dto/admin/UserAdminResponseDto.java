package com.devtony.app.dto.admin;

import com.devtony.app.dto.user.UserResponseDto;

import java.util.Set;

public class UserAdminResponseDto extends UserResponseDto {
    private Set<String> roles;

    public UserAdminResponseDto(Long id, String name, String email, Set<String> roles) {
        super(id, name, email);
        this.roles = roles;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
