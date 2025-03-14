package com.devtony.app.repository;

import com.devtony.app.model.Event;
import com.devtony.app.repository.projections.EventProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IEventRepository extends JpaRepository<Event, Long> {
    List<EventProjection> findAllBy();
    boolean existsByName(String name);

    Optional<Event> findByName(String name);
}
