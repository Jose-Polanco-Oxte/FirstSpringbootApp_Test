package com.devtony.app.repository;

import com.devtony.app.model.EventInvitation;
import com.devtony.app.repository.projections.MyInvitationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IEventInvitationRepository extends JpaRepository<EventInvitation, Long> {

    @Query("SELECT i FROM EventInvitation i WHERE i.user.id = :userId AND i.status = 'ACCEPTED'")
    List<MyInvitationProjection> findAllAcceptedInvitationsByUser(@Param("userId") Long userId);

    @Query("SELECT i FROM EventInvitation i WHERE i.user.id = :userId AND i.status = 'PENDING'")
    List<MyInvitationProjection> findAllPendingInvitationsByUser(@Param("userId") Long userId);

    @Query("SELECT i FROM EventInvitation i WHERE i.user.id = :userId AND i.status = 'REJECTED'")
    List<MyInvitationProjection> findAllRejectedInvitationsByUser(@Param("userId") Long userId);

    @Query("SELECT i FROM EventInvitation i WHERE i.user.id = :userId AND i.event.id = :eventId")
    Optional<EventInvitation> findAcceptedInvitation(@Param("userId") Long userId, @Param("eventId") Long eventId);

    @Query("SELECT i FROM EventInvitation i WHERE i.event.id = :eventId AND i.user.id = :userId")
    Optional<EventInvitation> findMySentInvitationByEventId(Long eventId, Long userId);
}
