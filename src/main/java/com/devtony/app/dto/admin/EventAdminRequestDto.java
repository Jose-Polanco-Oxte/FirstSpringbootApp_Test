package com.devtony.app.dto.admin;

import com.devtony.app.dto.events.EventRequestDto;

public class EventAdminRequestDto extends EventRequestDto {
    private String admin;

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
