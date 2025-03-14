package com.devtony.app.dto;

public class SimpleFormatBodyResponse {
    private String message;

    public SimpleFormatBodyResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}