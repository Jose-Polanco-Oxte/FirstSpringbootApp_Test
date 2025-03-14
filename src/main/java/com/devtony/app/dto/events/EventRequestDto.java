package com.devtony.app.dto.events;

import jakarta.validation.constraints.NotBlank;

public class EventRequestDto {

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String date;

    @NotBlank
    private String location;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
