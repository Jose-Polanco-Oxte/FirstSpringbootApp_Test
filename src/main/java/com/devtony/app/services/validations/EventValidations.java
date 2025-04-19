package com.devtony.app.services.validations;

import com.devtony.app.dto.events.EventRequestDto;
import com.devtony.app.exception.EventException;
import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.model.Event;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class EventValidations {
    public enum OperationType {
        DELETE("eliminar"),
        UPDATE("actualizar");

        private final String spanishTranslation;

        OperationType(String spanishTranslation) {
            this.spanishTranslation = spanishTranslation;
        }

        public String getSpanishTranslation() {
            return spanishTranslation;
        }
    }

    public <T extends Event> void validateEditionEvent(T event, Long userId, OperationType operation) throws EventException {
        if (!event.getCreator().getId().equals(userId)) {
            throw new EventException("You are not authorized to " + operation.name().toLowerCase() + " this event",
                    new ExceptionDetails("No tienes autorización para" + operation.spanishTranslation + "este evento", "low"));
        }
    }
    public void validateEventsReturn(List<?> events) throws EventException {
        if (events.isEmpty()) {
            throw new EventException("No events found",
                    new ExceptionDetails("No se encontraron eventos", "low"));
        }
    }

    public void validateCreationEvent(EventRequestDto eventRequestDto) throws EventException {
        if (eventRequestDto.getName().isEmpty() || eventRequestDto.getDescription().isEmpty() || eventRequestDto.getDate().isEmpty()) {
            throw new EventException("Invalid event data",
                    new ExceptionDetails("Invalid event data", "low"));
        }
    }

    public void validateUsersInvitedReturn(List<?> users) throws EventException {
        if (users.isEmpty()) {
            throw new EventException("No users found",
                    new ExceptionDetails("No se encontraron invitados confirmados", "low"));
        }
    }
}
