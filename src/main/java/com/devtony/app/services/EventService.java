package com.devtony.app.services;

import com.devtony.app.dto.events.AllEventsResponseDto;
import com.devtony.app.dto.events.EventRequestDto;
import com.devtony.app.dto.events.EventResponseDto;
import com.devtony.app.exception.EventException;
import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.exception.UserAuthException;
import com.devtony.app.model.Event;
import com.devtony.app.model.User;
import com.devtony.app.repository.IEventRepository;
import com.devtony.app.repository.IUserRepository;
import com.devtony.app.services.interfaces.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EventService implements IEventService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IEventRepository eventRepository;

    @Override
    public AllEventsResponseDto getAllEvents() {
        long count = eventRepository.count();
        if (count == 0) {
            throw new EventException("No events found",
                    new ExceptionDetails("No existen eventos creados actualmente", "low"));
        }
        return new AllEventsResponseDto(count, eventRepository.findAllBy());
    }

    @Override
    public EventResponseDto getEvent(Long eventID) {
        Event evento = eventRepository.findById(eventID)
                .orElseThrow(() -> new EventException("Event not found",
                        new ExceptionDetails("El evento no ha sido encontrado", "low")));

        return new EventResponseDto(evento.getId(), evento.getName(), evento.getDescription(), String.valueOf(evento.getDate()));
    }

    @Override
    public void createEvent(EventRequestDto event) {
        AuxiliarAuthService authService = new AuxiliarAuthService();
        if (eventRepository.findByName(event.getName()).isPresent()) {
            throw new EventException("Event already exists",
                    new ExceptionDetails("El evento ya existe", "low"));
        }
        Event evento = new Event();
        User user = userRepository.findById(authService.getId())
                .orElseThrow(() -> new UserAuthException("User not found",
                        new ExceptionDetails("La administración del evento no pudo asignarse porque el usuario no se ha encontrado en la base de datos",
                                "medium")));
        evento.setCreator(user);
        evento.setName(event.getName());
        evento.setDescription(event.getDescription());
        evento.setDate(Instant.parse(event.getDate()));
        evento.setLocation(event.getLocation());
        eventRepository.save(evento);
    }

    @Override
    public void deleteEvent(Long eventID) {
        AuxiliarAuthService authService = new AuxiliarAuthService();
        Event evento = eventRepository.findById(eventID)
                .orElseThrow(() -> new EventException("Event not found",
                        new ExceptionDetails("El evento no ha sido encontrado", "low")));

        if (!evento.getCreator().getId().equals(authService.getId())) {
            throw new EventException("Unauthorized",
                    new ExceptionDetails("No tienes permisos para eliminar este evento", "high"));
        }
        eventRepository.delete(evento);
    }

    @Override
    public void updateEvent(EventRequestDto eventRequestDto) {
        AuxiliarAuthService authService = new AuxiliarAuthService();
        Event evento = eventRepository.findByName(eventRequestDto.getName())
                .orElseThrow(() -> new EventException("Event not found",
                        new ExceptionDetails("El evento no ha sido encontrado", "low")));

        if (!evento.getCreator().getId().equals(authService.getId())) {
            throw new EventException("Unauthorized",
                    new ExceptionDetails("No tienes permisos para editar este evento", "high"));
        }

        evento.setName(eventRequestDto.getName());
        evento.setDescription(eventRequestDto.getDescription());
        evento.setDate(Instant.parse(eventRequestDto.getDate()));
        evento.setLocation(eventRequestDto.getLocation());
        eventRepository.save(evento);
    }
}