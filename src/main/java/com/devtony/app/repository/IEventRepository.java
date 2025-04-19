package com.devtony.app.repository;

import com.devtony.app.model.Event;
import com.devtony.app.repository.projections.EventProjection;
import com.devtony.app.repository.projections.InvitationEventProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IEventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e.id AS id, e.name AS name, e.date AS date, " +
            "e.description AS description, e.location AS location, " +
            "e.image AS image, u.name AS creatorName, u.id AS creatorId " +
            "FROM Event e " +
            "JOIN e.creator u " +
            "ORDER BY e.id ASC")
    Page<EventProjection> selectWithPagination(Pageable pageable);

    @Query("SELECT e.id AS id, e.name AS name, e.date AS date, " +
            "e.description AS description, e.location AS location, " +
            "e.image AS image, u.name AS creatorName, u.id AS creatorId " +
            "FROM Event e " +
            "JOIN e.creator u " +
            "WHERE e.id > :eventId " +
            "ORDER BY e.id ASC " +
            "LIMIT :limit")
    List<EventProjection> selectWithCursor(@Param("eventId") Long eventId, @Param("limit") int limit);

    @Query("SELECT COUNT(e) > 0 FROM Event e WHERE e.id > :eventId")
    boolean existsNext(@Param("eventId") Long eventId);

    Optional<Event> findByName(String name);

    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.invitations WHERE e.id = :eventId")
    Optional<Event> findByIdWithInvitations(@Param("eventId") Long eventId);


    @Query("SELECT e.id AS id, e.name AS name, e.date AS date, " +
            "e.description AS description, e.location AS location, " +
            "e.image AS image, u.name AS creatorName, u.id AS creatorId " +
            "FROM Event e " +
            "JOIN e.creator u " +
            "WHERE e.creator.id = :userId " +
            "ORDER BY e.id ASC")
    Page<EventProjection> selectMyEventsWithPagination(Pageable pageable, @Param("userId") Long creatorId);

    @Query("SELECT e.id AS id, e.name AS name, e.date AS date, " +
            "e.description AS description, e.location AS location, " +
            "e.image AS image, u.name AS creatorName, u.id AS creatorId " +
            "FROM Event e " +
            "JOIN e.creator u " +
            "WHERE e.id > :eventId AND e.creator.id = :userId " +
            "ORDER BY e.id ASC " +
            "LIMIT :limit")
    List<EventProjection> selectMyEventsWithCursor(@Param("eventId") Long eventId, @Param("limit") int limit, @Param("userId") Long creatorId);


    @Query("SELECT u.id AS userId, u.name AS userName, u.email AS userEmail " +
            "FROM Attendance a " +
            "JOIN a.invitation ei " +
            "JOIN ei.user u " +
            "WHERE ei.event.id = :eventId " +
            "AND u.id > :lastUserId " +
            "ORDER BY u.id ASC")
    List<InvitationEventProjection> findEventInvitationsByEventIdWithCursor(@Param("eventId") Long eventId, @Param("lastUserId") Long lastUserId, @Param("limit") int limit);

    @Query("SELECT u.id AS userId, u.name AS userName, u.email AS userEmail " +
            "FROM Attendance a " +
            "JOIN a.invitation ei " +
            "JOIN ei.user u " +
            "WHERE ei.event.id = :eventId " +
            "ORDER BY u.id ASC")
    Page<InvitationEventProjection> findEventInvitationsByEventIdWithPagination(@Param("eventId") Long eventId, Pageable pageable);

    @Query("SELECT COUNT(u) > 0 FROM Attendance a " +
            "JOIN a.invitation ei " +
            "JOIN ei.user u " +
            "WHERE ei.event.id = :eventId " +
            "AND u.id > :lastUserId")
    boolean existsNextInvitation(@Param("eventId") Long eventId, @Param("lastUserId") Long lastUserId);
}