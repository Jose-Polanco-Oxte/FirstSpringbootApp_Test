package com.devtony.app.dto.events;

import com.devtony.app.repository.projections.EventProjection;

import java.util.List;

public class PageEventResponseDto<TypeOfProjection> {
    private Long lastId;
    private boolean hasNext;
    List<TypeOfProjection> events;

    public PageEventResponseDto(Long lastId, boolean hasNext, List<TypeOfProjection> events) {
        this.lastId = lastId;
        this.hasNext = hasNext;
        this.events = events;
    }

    public Long getLastId() {
        return lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<TypeOfProjection> getEvents() {
        return events;
    }

    public void setEvents(List<TypeOfProjection> events) {
        this.events = events;
    }
}
