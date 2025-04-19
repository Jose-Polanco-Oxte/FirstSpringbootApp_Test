package com.devtony.app.controllers;

import com.devtony.app.dto.events.EventRequestDto;
import com.devtony.app.dto.events.EventResponseDto;
import com.devtony.app.dto.SimpleFormatBodyResponse;
import com.devtony.app.dto.events.PageEventResponseDto;
import com.devtony.app.repository.projections.EventProjection;
import com.devtony.app.repository.projections.InvitationEventProjection;
import com.devtony.app.services.interfaces.IEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    private final IEventService eventService;

    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/my-events")
    public ResponseEntity<PageEventResponseDto<EventProjection>> myEvents(
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(required = false) Long cursor) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(eventService.getMyEvents(cursor, limit));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/get/{eventID}")
    public ResponseEntity<EventResponseDto> getEvent(@PathVariable Long eventID) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(eventService.getEvent(eventID));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('ORG')")
    @PostMapping("/create")
    public ResponseEntity<SimpleFormatBodyResponse> createEvent(@Validated @RequestBody EventRequestDto event) {
        eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SimpleFormatBodyResponse("El evento se ha creado correctamente"));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('ORG')")
    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<SimpleFormatBodyResponse> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SimpleFormatBodyResponse("El evento se ha eliminado correctamente"));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('ORG')")
    @PostMapping("/edit/{eventId}")
    public ResponseEntity<SimpleFormatBodyResponse> editEvent(@Validated @RequestBody EventRequestDto eventRequestDto, @PathVariable Long eventId) {
        eventService.updateEvent(eventRequestDto, eventId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SimpleFormatBodyResponse("El evento se ha editado correctamente"));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public ResponseEntity<PageEventResponseDto<EventProjection>> getAllEvents(
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(required = false) Long cursor) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(eventService.getAllEvents(cursor, limit));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('ORG')")
    @GetMapping("/get-event-invitations/{eventId}")
    public ResponseEntity<PageEventResponseDto<InvitationEventProjection>> getEventInvitations (
            @PathVariable Long eventId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) Long cursor) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(eventService.eventInvitation(eventId, cursor, limit));
    }

}