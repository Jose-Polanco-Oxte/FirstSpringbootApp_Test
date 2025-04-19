package com.devtony.app.services.validations;

import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.exception.InvitationException;
import com.devtony.app.model.EventInvitation;
import com.devtony.app.repository.projections.MyInvitationProjection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InvitationValidation {

    public enum Status {
        ACCEPT ("aceptar", "aceptada", "ACCEPTED"),
        REJECT ("rechazada", "rechazada", "REJECTED");

        private final String spanishTranslation;
        private final String participio;
        private final String participle;

        Status(String spanishTranslation, String participio, String participle) {
            this.spanishTranslation = spanishTranslation;
            this.participio = participio;
            this.participle = participle;
        }

        public String getSpanishTranslation() {
            return spanishTranslation;
        }

        public String getParticipio() {
            return participio;
        }

        public String getParticiple() {
            return participle;
        }
    }

    public void validateInvitationListReturn(List<MyInvitationProjection> invitations) throws InvitationException {
        if (invitations.isEmpty()) {
            throw new InvitationException("No invitations found",
                    new ExceptionDetails("No existen invitaciones actualmente", "low"));
        }
    }

    public void validateInvitation(EventInvitation eventInvitation, Long userId, Status status) throws InvitationException {
        if (!eventInvitation.getUser().getId().equals(userId)) {
            throw new InvitationException("Not allowed to " + status.name().toLowerCase() + " this invitation",
                    new ExceptionDetails("No tienes permisos para " + status.spanishTranslation + " esta invitación", "low"));
        }
        if (eventInvitation.getStatus().equals(status.participle)) {
            throw new InvitationException("The invitation has already been " + status.participle.toLowerCase(),
                    new ExceptionDetails("La invitación ya ha sido " + status.participio, "low"));
        }
    }
}
