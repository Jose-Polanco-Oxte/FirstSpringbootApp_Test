package com.devtony.app.repository;

import com.devtony.app.model.EventInvitation;
import com.devtony.app.repository.projections.InvitationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IEventInvitationRepository extends JpaRepository<EventInvitation, Long> {

    @Query("SELECT i FROM EventInvitation i WHERE i.user.id = :userId AND i.status = 'ACCEPTED'")
    Optional<List<InvitationProjection>> findAllAcceptedInvitationsByUser(@Param("userId") Long userId);

    @Query("SELECT i FROM EventInvitation i WHERE i.user.id = :userId AND i.status = 'PENDING'")
    Optional<List<InvitationProjection>> findAllPendingInvitationsByUser(@Param("userId") Long userId);

    @Query("SELECT i FROM EventInvitation i WHERE i.user.id = :userId AND i.status = 'REJECTED'")
    Optional<List<InvitationProjection>> findAllRejectedInvitationsByUser(@Param("userId") Long userId);

    @Query("SELECT i FROM EventInvitation i WHERE i.user.id = :userId AND i.event.id = :eventId")
    Optional<EventInvitation> findAcceptedInvitation(@Param("userId") Long userId, @Param("eventId") Long eventId);

    boolean existsByEvent_IdAndUser_Id(Long eventId, Long userId);
}
