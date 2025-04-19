package com.devtony.app.dto.user;

import java.util.Set;

public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String qrCode;

    public UserResponseDto(Long id, String name, String email, String qrCode) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.qrCode = qrCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
