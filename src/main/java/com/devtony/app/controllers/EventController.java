package com.devtony.app.controllers;

import com.devtony.app.dto.events.AllEventsResponseDto;
import com.devtony.app.dto.events.EventRequestDto;
import com.devtony.app.dto.events.EventResponseDto;
import com.devtony.app.dto.SimpleFormatBodyResponse;
import com.devtony.app.services.interfaces.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private IEventService eventService;

    @PreAuthorize("permitAll()")
    @GetMapping("/get-all")
    public ResponseEntity<AllEventsResponseDto> getAllEvents() {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getAllEvents());
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/get/{eventID}")
    public ResponseEntity<EventResponseDto> getEvent(@PathVariable Long eventID) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEvent(eventID));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER')")
    @PostMapping("/create")
    public ResponseEntity<SimpleFormatBodyResponse> createEvent(@Validated @RequestBody EventRequestDto event) {
        eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SimpleFormatBodyResponse("El evento se ha creado correctamente"));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER')")
    @DeleteMapping("/delete/{eventID}")
    public ResponseEntity<SimpleFormatBodyResponse> deleteEvent(@PathVariable Long eventID) {
        eventService.deleteEvent(eventID);
        return ResponseEntity.status(HttpStatus.OK).body(new SimpleFormatBodyResponse("El evento se ha eliminado correctamente"));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER')")
    @PostMapping("/edit-event")
    public ResponseEntity<SimpleFormatBodyResponse> editEvent(@Validated @RequestBody EventRequestDto eventRequestDto) {
        eventService.updateEvent(eventRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new SimpleFormatBodyResponse("El evento se ha editado correctamente"));
    }

}