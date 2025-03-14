package com.devtony.app.dto.admin;

import com.devtony.app.dto.events.EventResponseDto;

public class EventAdminResponseDto extends EventResponseDto {
    private String admin;

    public EventAdminResponseDto(Long id, String name, String description, String date, String admin) {
        super(id, name, description, date);
        this.admin = admin;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
