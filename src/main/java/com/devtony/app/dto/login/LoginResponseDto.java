package com.devtony.app.dto.login;

public class LoginResponseDto {
    private String token;
    private String name;
    private String email;
    private String qrCode;

    public LoginResponseDto(String token, String name, String email, String qrCode) {
        this.token = token;
        this.name = name;
        this.email = email;
        this.qrCode = qrCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
