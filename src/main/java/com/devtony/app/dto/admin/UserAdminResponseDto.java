package com.devtony.app.dto.admin;

import com.devtony.app.dto.user.UserResponseDto;

import java.util.Set;

public class UserAdminResponseDto extends UserResponseDto {
    private Set<String> roles;

    public UserAdminResponseDto(Long id, String name, String email, String qrCode ,Set<String> roles) {
        super(id, name, email, qrCode);
        this.roles = roles;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
