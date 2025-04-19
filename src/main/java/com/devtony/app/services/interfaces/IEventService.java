package com.devtony.app.services.interfaces;

import com.devtony.app.dto.events.EventRequestDto;
import com.devtony.app.dto.events.EventResponseDto;
import com.devtony.app.dto.events.PageEventResponseDto;
import com.devtony.app.repository.projections.EventProjection;
import com.devtony.app.repository.projections.InvitationEventProjection;

import java.util.List;

public interface IEventService {
    public PageEventResponseDto<EventProjection> getAllEvents(Long cursor, int limit);
    public PageEventResponseDto<EventProjection> getMyEvents(Long cursor, int limit);
    public EventResponseDto getEvent(Long eventId);
    public void createEvent(EventRequestDto event);
    public void deleteEvent(Long eventId);
    public void updateEvent(EventRequestDto eventRequestDto, Long eventId);
    public PageEventResponseDto<InvitationEventProjection> eventInvitation(Long eventId, Long cursor, int limit);
}
