package com.devtony.app.services;

import com.devtony.app.dto.events.EventRequestDto;
import com.devtony.app.dto.events.EventResponseDto;
import com.devtony.app.dto.events.PageEventResponseDto;
import com.devtony.app.exception.EventException;
import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.exception.UserAuthException;
import com.devtony.app.model.Event;
import com.devtony.app.model.User;
import com.devtony.app.repository.IEventRepository;
import com.devtony.app.repository.IUserRepository;
import com.devtony.app.repository.projections.EventProjection;
import com.devtony.app.repository.projections.InvitationEventProjection;
import com.devtony.app.services.interfaces.IEventService;
import com.devtony.app.services.validations.EventValidations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;

@Service
public class EventService implements IEventService {

    private final IUserRepository userRepository;
    private final IEventRepository eventRepository;
    private final EventValidations validations;
    private final AuxiliarAuthService authService;

    public EventService(IUserRepository userRepository, IEventRepository eventRepository, EventValidations validations, AuxiliarAuthService authService) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.validations = validations;
        this.authService = authService;
    }

    @Override
    public PageEventResponseDto<EventProjection> getAllEvents(Long cursor, int limit) throws EventException{
        Long lastId;
        boolean hasNext;
        List<EventProjection> events;
        if (cursor == null) {
            Page<EventProjection> pages = eventRepository.selectWithPagination(PageRequest.of(0, limit));
            events = pages.getContent();
            lastId = pages.getContent().getLast().getId();
            hasNext = pages.hasNext();
        } else {
            events = eventRepository.selectWithCursor(cursor, limit);
            lastId = events.getLast().getId();
            hasNext = eventRepository.existsNext(lastId);
        }
        validations.validateEventsReturn(events);
        return new PageEventResponseDto<>(lastId, hasNext, events);
    }

    @Override
    public PageEventResponseDto<EventProjection> getMyEvents(Long cursor, int limit) throws EventException {
        Long lastId;
        boolean hasNext;
        List<EventProjection> events;
        if (cursor == null) {
            Page<EventProjection> pages = eventRepository.selectMyEventsWithPagination(PageRequest.of(0, limit), authService.getId());
            events = pages.getContent();
            lastId = pages.getContent().getLast().getId();
            hasNext = pages.hasNext();
        } else {
            events = eventRepository.selectMyEventsWithCursor(cursor, limit, authService.getId());
            lastId = events.getLast().getId();
            hasNext = eventRepository.existsNext(lastId);
        }
        validations.validateEventsReturn(events);
        return new PageEventResponseDto<>(lastId, hasNext, events);
    }

    @Override
    public EventResponseDto getEvent(Long eventId) throws EventException {
        Event evento = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException("Event not found",
                        new ExceptionDetails("El evento no ha sido encontrado", "low")));
        return new EventResponseDto(evento.getId(), evento.getName(), evento.getDescription(), String.valueOf(evento.getDate()));
    }

    @Transactional
    @Override
    public void createEvent(EventRequestDto eventRequestDto) throws EventException {
        eventRepository.findByName(eventRequestDto.getName()).ifPresent(user -> {
            throw new EventException("Event already exists",
                    new ExceptionDetails("El evento ya existe", "low"));
        });
        Event evento = new Event();
        User user = userRepository.findById(authService.getId())
                .orElseThrow(() -> new UserAuthException("User not found",
                        new ExceptionDetails("La administración del evento no pudo asignarse porque el usuario no se ha encontrado en la base de datos",
                                "medium")));
        evento.setCreator(user);
        evento.setName(eventRequestDto.getName());
        evento.setDescription(eventRequestDto.getDescription());
        evento.setDate(Instant.parse(eventRequestDto.getDate()));
        evento.setLocation(eventRequestDto.getLocation());
        eventRepository.save(evento);
    }

    @Transactional
    @Override
    public void updateEvent(EventRequestDto eventRequestDto, Long eventId) throws EventException {
        Event evento = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException("Event not found",
                        new ExceptionDetails("El evento no ha sido encontrado", "low")));

        validations.validateEditionEvent(evento, authService.getId(), EventValidations.OperationType.UPDATE);
        evento.setName(eventRequestDto.getName());
        evento.setDescription(eventRequestDto.getDescription());
        evento.setDate(Instant.parse(eventRequestDto.getDate()));
        evento.setLocation(eventRequestDto.getLocation());
        eventRepository.save(evento);
    }

    @Transactional
    @Override
    public void deleteEvent(Long eventId) throws EventException {
        /*Enviar notificaciones a todos los invitados sobre la eliminación del evento*/
        Event evento = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException("Event not found",
                        new ExceptionDetails("El evento no ha sido encontrado", "low")));
        validations.validateEditionEvent(evento, authService.getId(), EventValidations.OperationType.DELETE);
        eventRepository.deleteById
    }

    @Override
    public PageEventResponseDto<InvitationEventProjection> eventInvitation(Long eventId, Long cursor, int limit) throws EventException {
        Long lastId;
        boolean hasNext;
        List<InvitationEventProjection> users;
        if (cursor == null) {
            Page<InvitationEventProjection> pages = eventRepository.findEventInvitationsByEventIdWithPagination(eventId, PageRequest.of(0, limit));
            users = pages.getContent();
            lastId = pages.getContent().getLast().getUserId();
            hasNext = pages.hasNext();
        } else {
            users = eventRepository.findEventInvitationsByEventIdWithCursor(eventId, cursor, limit);
            lastId = users.getLast().getUserId();
            hasNext = eventRepository.existsNextInvitation(eventId, lastId);
        }
        validations.validateUsersInvitedReturn(users);
        return new PageEventResponseDto<>(lastId, hasNext, users);

    }
}