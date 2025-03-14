package com.devtony.app.dto.events;

import com.devtony.app.model.Event;
import com.devtony.app.repository.projections.EventProjection;

import java.util.List;

public class AllEventsResponseDto {
    private Long total;
    private List<EventProjection> eventos;

    public AllEventsResponseDto(Long total, List<EventProjection> eventos) {
        this.total = total;
        this.eventos = eventos;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<EventProjection> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventProjection> eventos) {
        this.eventos = eventos;
    }
}
