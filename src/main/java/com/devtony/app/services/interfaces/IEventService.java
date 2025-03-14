package com.devtony.app.services.interfaces;

import com.devtony.app.dto.events.AllEventsResponseDto;
import com.devtony.app.dto.events.EventRequestDto;
import com.devtony.app.dto.events.EventResponseDto;

public interface IEventService {
    public AllEventsResponseDto getAllEvents();
    public EventResponseDto getEvent(Long eventID);
    public void createEvent(EventRequestDto event);
    public void deleteEvent(Long eventID);
    public void updateEvent(EventRequestDto eventRequestDto);
}
